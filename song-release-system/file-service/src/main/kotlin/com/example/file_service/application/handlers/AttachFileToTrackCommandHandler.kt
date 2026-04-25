package com.example.file_service.application.handlers

import com.example.common.commands.CommandHandler
import com.example.common.messaging.EventPublisher
import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.domain.commands.AttachFileToTrackCommand
import com.example.file_service.domain.event.outgoing.FileAttachedEvent
import com.example.file_service.domain.model.MusicFile
import org.slf4j.LoggerFactory

class AttachFileToTrackCommandHandler(
    private val eventPublisher: EventPublisher,
    private val repository: MusicFilesRepository
): CommandHandler<AttachFileToTrackCommand, Unit> {

    private val logger = LoggerFactory.getLogger(AttachFileToTrackCommandHandler::class.java)

    override fun handle(command: AttachFileToTrackCommand) {
        logger.info("Executing AttachFileToTrackUseCase for trackId=${command.trackId}, fileName=${command.fileName}")

        val mf = MusicFile(
            trackId = command.trackId,
            fileName = command.fileName,
            file = command.file
        )
        logger.debug("Successfully validated and created MusicFile domain model")

        val filePath: String = repository.save(mf)
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