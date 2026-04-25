package com.example.file_service.domain.event.outgoing

import com.example.common.event.Event
import java.time.Instant
import java.util.UUID

/**
 * Event published when a music file was attached to a track.
 */
data class FileAttachedEvent(
    val trackId: UUID,
    val path: String,
    override val timestamp: Instant = Instant.now(),
) : Event