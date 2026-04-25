package com.example.notification_service.domain.event.incoming

import com.example.common.event.Event
import java.time.Instant
import java.util.UUID

/**
 * An event indicating that a track has been successfully uploaded and is now available for streaming.
 * After receiving it from `track-service` followers are to be notified.
 */
data class TrackUploadedEvent(
    val trackId: UUID,
    val title: String,
    val artistId: UUID,
    val keywords: List<String>,
    override val timestamp: Instant = Instant.now()
) : Event
