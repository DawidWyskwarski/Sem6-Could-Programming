package com.example.track_service.application.handlers

import com.example.common.commands.CommandHandler
import com.example.track_service.application.ports.TrackRepository
import com.example.track_service.domain.commands.CreateTrackCommand
import com.example.track_service.domain.model.Track
import org.slf4j.LoggerFactory
import java.util.UUID

class CreateTrackCommandHandler(
    private val repository: TrackRepository
) : CommandHandler<CreateTrackCommand, UUID> {

    private val logger = LoggerFactory.getLogger(CreateTrackCommandHandler::class.java)

    override fun handle(command: CreateTrackCommand): UUID {
        logger.info("Executing CreateTrackUseCase for title='${command.title}', artistId=${command.artistId}, keywordsCount=${command.keywords.size}")

        val track = Track(
            title = command.title,
            artistId = command.artistId,
            keywords = command.keywords,
        )
        logger.debug("Successfully created Track domain model with id={}", track.id)

        repository.save(track)
        logger.info("Saved initial track metadata for trackId={}", track.id)

        return track.id
    }
}