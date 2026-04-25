package com.example.tag_service.domain.event.outgoing

import com.example.common.event.Event
import java.time.Instant
import java.util.UUID

/**
 * After the track is tagged, this event is emitted to notify other services (like `playlist-service`) about the new tags associated with the track.
 */
data class TrackTaggedEvent(
    val trackId: UUID,
    val tagIds: Set<UUID>,
    override val timestamp: Instant = Instant.now()
) : Event