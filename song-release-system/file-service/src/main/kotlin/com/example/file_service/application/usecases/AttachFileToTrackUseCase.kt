package com.example.file_service.application.usecases

import com.example.common.messaging.EventPublisher
import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.domain.event.outgoing.FileAttachedEvent
import com.example.file_service.domain.model.MusicFile
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * Use case for attaching a music file to a track.
 * This involves validating the input, saving the file using the repository,
 * and publishing an event to notify other services of the attachment.
 */
class AttachFileToTrackUseCase(
    private val eventPublisher: EventPublisher,
    private val repository: MusicFilesRepository
) {

    private val logger = LoggerFactory.getLogger(AttachFileToTrackUseCase::class.java)

    fun execute(trackId: UUID, fileName: String, file: ByteArray) {
        logger.info("Executing AttachFileToTrackUseCase for trackId={}, fileName={}", trackId, fileName)

        val mf = MusicFile(
            trackId = trackId,
            fileName = fileName,
            file = file
        )
        logger.debug("Successfully validated and created MusicFile domain model")

        val filePath: String = repository.save(mf)
        logger.info("Saved MusicFile successfully to path: {}", filePath)

        eventPublisher.publish(
            FileAttachedEvent(
                trackId = trackId,
                path = filePath
            )
        )
        logger.info("Published FileAttachedEvent for trackId={}", trackId)
    }
}