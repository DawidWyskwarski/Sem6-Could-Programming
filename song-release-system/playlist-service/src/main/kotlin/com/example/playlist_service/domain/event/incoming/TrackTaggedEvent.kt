package com.example.playlist_service.domain.event.incoming

import com.example.common.event.Event
import java.util.UUID
import java.time.Instant

/**
 * An event indicating that tags were assigned to the track and is ready to be position in global playlists.
 */
data class TrackTaggedEvent(
    val trackId: UUID,
    val tagIds: Set<UUID>,
    override val timestamp: Instant = Instant.now()
) : Event