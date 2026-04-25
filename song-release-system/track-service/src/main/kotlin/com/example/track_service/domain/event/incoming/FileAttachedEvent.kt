package com.example.track_service.domain.event.incoming

import com.example.common.event.Event
import java.time.Instant
import java.util.UUID

/**
 * An event indicating that a file has been attached to a track.
 * This event is emitted by the file service when a file is successfully uploaded and associated with a track.
 */
data class FileAttachedEvent(
    val trackId: UUID,
    val path: String,
    override val timestamp: Instant = Instant.now(),
) : Event