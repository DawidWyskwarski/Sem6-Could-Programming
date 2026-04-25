package com.example.track_service.domain.commands

import com.example.common.commands.Command
import java.time.Instant
import java.util.UUID

class CreateTrackCommand(
    val title: String,
    val artistId: UUID,
    val keywords: List<String>,
    override val timestamp: Instant = Instant.now()
): Command<UUID>