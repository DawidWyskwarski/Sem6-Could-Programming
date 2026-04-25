package com.example.tag_service.domain.commands

import com.example.common.commands.Command
import java.time.Instant
import java.util.UUID

class AssignTagsCommand(
    val trackId: UUID,
    val keywords: List<String>,
    override val timestamp: Instant = Instant.now()
): Command<Unit>