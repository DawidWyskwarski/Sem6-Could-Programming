package com.example.tag_service.domain.event.incoming

import com.example.common.event.Event
import java.time.Instant
import java.util.UUID

/**
 * An event indicating that a track has been successfully uploaded and is now available for streaming.
 * After receiving it from `track-service` the track is being tagged (tag is like a system keywords) based on (raw) keywords.
 */
data class TrackUploadedEvent(
    val trackId: UUID,
    val title: String,
    val artistId: UUID,
    val keywords: List<String>,
    override val timestamp: Instant = Instant.now()
) : Event
