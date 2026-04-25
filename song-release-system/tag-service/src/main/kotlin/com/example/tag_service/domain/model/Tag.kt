package com.example.tag_service.domain.model

import java.util.UUID

/**
 * Simple data class representing a tag (system keyword)
 */
data class Tag(
    val id: UUID,
    val name: String
)