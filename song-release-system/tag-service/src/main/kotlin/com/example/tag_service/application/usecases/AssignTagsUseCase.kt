package com.example.tag_service.application.usecases

import com.example.common.messaging.EventPublisher
import com.example.tag_service.application.ports.TagRepository
import com.example.tag_service.domain.event.outgoing.TrackTaggedEvent
import com.example.tag_service.domain.model.Tag
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * Use case responsible for assigning tags to a specific track based on a list of keywords.
 *
 * It transforms the provided keywords to lowercase, retrieves matching domain [Tag]s from the
 * [TagRepository], and links them to the track. If any tags are successfully assigned,
 * it publishes a [TrackTaggedEvent] to notify downstream processing components.
 *
 * @property repository Defines operations for fetching and assigning tag records.
 * @property eventPublisher Handles the broadcasting of domain events.
 */
class AssignTagsUseCase(
    private val repository: TagRepository,
    private val eventPublisher: EventPublisher
) {
    private val logger = LoggerFactory.getLogger(AssignTagsUseCase::class.java)

    /**
     * Executes the assignment logic for a given track and its keywords.
     *
     * @param trackId The unique [UUID] identifier of the target track.
     * @param keywords A list of keyword strings describing the track, used to find existing tags.
     */
    fun execute(trackId: UUID, keywords: List<String>) {
        logger.info("Executing AssignTagsUseCase for trackId={} with keywords={}", trackId, keywords)

        val upperKeywords = keywords.map { it.lowercase() }

        val tagsToAssign: List<Tag> = repository.getTagsByNames(upperKeywords)
        logger.info("Found {} matching tags in the repository", tagsToAssign.size)

        if (!tagsToAssign.isEmpty()) {
            logger.debug("Assigning tags to trackId={}: {}", trackId, tagsToAssign.map { it.name })
            repository.assignTags(tagsToAssign, trackId)

            logger.info("Publishing TrackTaggedEvent for trackId={}", trackId)
            eventPublisher.publish(
                TrackTaggedEvent(
                    trackId = trackId,
                    tagIds = tagsToAssign.map { it.id }.toSet()
                )
            )
        } else {
            logger.info("No matching tags found for trackId={}, skipping assignment and event publication", trackId)
        }
    }
}