package com.example.track_service.application.handlers

import com.example.common.commands.CommandHandler
import com.example.common.messaging.EventPublisher
import com.example.track_service.application.ports.TrackRepository
import com.example.track_service.domain.commands.MarkTrackAsUploadedCommand
import com.example.track_service.domain.event.outgoing.TrackUploadedEvent
import com.example.track_service.domain.model.Track
import org.slf4j.LoggerFactory

class MarkTrackAsUploadedCommandHandler(
    private val publisher: EventPublisher,
    private val repository: TrackRepository
) : CommandHandler<MarkTrackAsUploadedCommand, Unit> {

    private val logger = LoggerFactory.getLogger(MarkTrackAsUploadedCommandHandler::class.java)

    override fun handle(command: MarkTrackAsUploadedCommand) {
        val trackId = command.trackId

        logger.info("Executing OnFileAttachedUseCase for trackId={}", trackId)

        // If track is not found, we log a warning and ignore the event
        var track: Track = repository.findById(trackId) ?: run {
            logger.warn("No Track with id={} found in the repository, ignoring FileAttachedEvent", trackId)
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