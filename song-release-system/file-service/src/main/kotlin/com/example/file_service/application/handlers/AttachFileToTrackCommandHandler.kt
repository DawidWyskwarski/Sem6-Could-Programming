package com.example.file_service.application.handlers

import com.example.common.commands.CommandHandler
import com.example.common.messaging.EventPublisher
import com.example.file_service.application.ports.FileStorage
import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.domain.commands.AttachFileToTrackCommand
import com.example.file_service.domain.event.outgoing.FileAttachedEvent
import com.example.file_service.domain.model.MusicFile
import org.slf4j.LoggerFactory

class AttachFileToTrackCommandHandler(
    private val eventPublisher: EventPublisher,
    private val repository: MusicFilesRepository,
    private val fileStorage: FileStorage
): CommandHandler<AttachFileToTrackCommand, Unit> {

    private val logger = LoggerFactory.getLogger(AttachFileToTrackCommandHandler::class.java)

    override fun handle(command: AttachFileToTrackCommand) {
        logger.info("Executing AttachFileToTrackCommandHandler for trackId=${command.trackId}, fileName=${command.file.name}")

        if (repository.exists(command.trackId)) {
            logger.warn("A file is already attached to trackId={}, rejecting new attachment", command.trackId)
            throw IllegalStateException("A file is already attached to this track")
        }

        val mf = MusicFile(
            trackId = command.trackId,
            file = command.file
        )

        logger.debug("Successfully validated and created MusicFile domain model")

        val filePath: String = fileStorage.store(mf)
        repository.save(mf, filePath)

        logger.info("Saved MusicFile successfully to path: {}", filePath)

        eventPublisher.publish(
            FileAttachedEvent(
                trackId = mf.trackId,
                path = filePath
            )
        )
        logger.info("Published FileAttachedEvent for trackId={}", mf.trackId)
    }
}