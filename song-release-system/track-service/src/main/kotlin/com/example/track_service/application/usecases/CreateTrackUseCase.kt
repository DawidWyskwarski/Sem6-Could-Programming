package com.example.track_service.application.usecases

import com.example.track_service.application.ports.TrackRepository
import com.example.track_service.domain.model.Track
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Use case for creating a new track with initial metadata.
 * This use case is responsible for validating the input data, and saving data to repository.
 */
class CreateTrackUseCase(
    private val repository: TrackRepository
) {
    private val logger = LoggerFactory.getLogger(CreateTrackUseCase::class.java)

    /**
     * Creates a new track with the provided title, artist ID, and keywords.
     *
     * @return ID of newly created track
     */
    fun execute(
        title: String,
        artistId: UUID,
        keywords: List<String>,
    ) : UUID {
        logger.info("Executing CreateTrackUseCase for title='{}', artistId={}, keywordsCount={}", title, artistId, keywords.size)

        val track = Track(
            title = title,
            artistId = artistId,
            keywords = keywords,
        )
        logger.debug("Successfully created Track domain model with id={}", track.id)

        repository.save(track)
        logger.info("Saved initial track metadata for trackId={}", track.id)

        return track.id
    }
}