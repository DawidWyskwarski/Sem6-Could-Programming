package com.example.track_service.domain.event.outgoing

import com.example.common.event.Event
import com.example.track_service.domain.model.TrackStatus.UPLOADED
import java.time.Instant
import java.util.UUID

/**
 * An event indicating that a track has been successfully uploaded and is now available for streaming.
 * This event is emitted by the track service after processing a FileAttachedEvent and updating the track status to [UPLOADED].
 */
data class TrackUploadedEvent(
    val trackId: UUID,
    val title: String,
    val artistId: UUID,
    val keywords: List<String>,
    override val timestamp: Instant = Instant.now()
) : Event