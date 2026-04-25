package com.example.track_service.application.usecases

import com.example.common.messaging.EventPublisher
import com.example.track_service.application.ports.TrackRepository
import com.example.track_service.domain.event.outgoing.TrackUploadedEvent
import com.example.track_service.domain.model.Track
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Use case for handling the FileAttachedEvent when a music file is attached to a track.
 * This use case is responsible for updating the track status to UPLOADED and publishing a TrackUploadedEvent.
 */
class OnFileAttachedUseCase(
    private val publisher: EventPublisher,
    private val repository: TrackRepository
) {
    private val logger = LoggerFactory.getLogger(OnFileAttachedUseCase::class.java)

    fun execute(trackId: UUID) {
        logger.info("Executing OnFileAttachedUseCase for trackId={}", trackId)

        // If track is not found, we log a warning and ignore the event
        var track: Track = repository.findById(trackId) ?: run {
            logger.warn("Track with id={} not found in the repository, ignoring FileAttachedEvent", trackId)
            return
        }

        track.markAsUploaded()
        logger.debug("Track marked as uploaded")

        repository.save(track)
        logger.info("Updated track status in database for trackId={}", track.id)

        publisher.publish(
            TrackUploadedEvent(
                trackId = track.id,
                artistId = track.artistId,
                title = track.title,
                keywords = track.keywords
            )
        )
        logger.info("Published TrackUploadedEvent for trackId={}", track.id)
    }
}