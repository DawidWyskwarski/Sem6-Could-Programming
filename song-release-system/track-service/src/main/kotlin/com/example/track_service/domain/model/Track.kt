package com.example.track_service.domain.model

import com.example.track_service.domain.exception.InvalidTrackKeywordsException
import com.example.track_service.domain.exception.InvalidTrackTitleException
import java.util.UUID

/**
 * Represents a domain model for a music track.
 *
 * This class encapsulates the core details of a track including its metadata,
 * association with an artist, and processing state. It enforces intrinsic domain
 * invariants during initialization to ensure the integrity of the track details.
 *
 * @property id The unique identifier of the track. Defaults to a randomly generated UUID.
 * @property artistId The unique identifier of the artist who owns the track.
 * @property title The title of the track. Must not be blank and cannot exceed 100 characters.
 * @property keywords A collection of descriptive keywords or tags. Must not exceed 20 keywords.
 * @param status The initial [TrackStatus] of the track. Defaults to [TrackStatus.PENDING].
 * @throws InvalidTrackTitleException if the [title] is blank or exceeds 100 characters.
 * @throws InvalidTrackKeywordsException if the [keywords] list contains more than 20 elements.
 */
class Track(
    val id: UUID = UUID.randomUUID(),
    val artistId: UUID,
    val title: String,
    val keywords: List<String>,
    status: TrackStatus = TrackStatus.PENDING
) {
    var status: TrackStatus = status
        private  set

    init {
        if (title.isBlank()) throw InvalidTrackTitleException("Track title cannot be blank")
        if (title.length > 100) throw InvalidTrackTitleException("Track title cannot have more than 100 characters")
        if (keywords.size > 20) throw InvalidTrackKeywordsException("Number of track tags must be less than 20")
    }

    fun markAsUploaded() {
        this.status = TrackStatus.UPLOADED
    }
}