package com.example.playlist_service.domain.commands

import com.example.common.commands.Command
import java.time.Instant
import java.util.UUID

class AddTrackToPlaylistCommand(
    val trackId: UUID,
    val trackTagIds: Set<UUID>,
    override val timestamp: Instant = Instant.now()
): Command<Unit>