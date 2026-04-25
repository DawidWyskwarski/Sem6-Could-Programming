package com.example.notification_service.domain.commands

import com.example.common.commands.Command
import java.time.Instant
import java.util.UUID

class NotifyFollowersCommand(
    val artistId: UUID,
    val trackId: UUID,
    val trackTitle: String,
    override val timestamp: Instant = Instant.now()
) : Command<Unit>