package com.example.tag_service.application.ports

import com.example.tag_service.domain.model.Tag
import java.util.UUID

/**
 * Port interface for Tag repository operations.
 * This interface defines the contract for retrieving Tag entities and assigning a track to them.
 */
interface TagRepository {
    fun getTagsByNames(names: List<String>): List<Tag>
    fun assignTags(tag: List<Tag>, trackId: UUID)
}