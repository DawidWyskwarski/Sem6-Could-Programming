package com.example.tag_service.application.handlers

import com.example.common.commands.CommandHandler
import com.example.common.messaging.EventPublisher
import com.example.tag_service.application.ports.TagRepository
import com.example.tag_service.domain.commands.AssignTagsCommand
import com.example.tag_service.domain.event.outgoing.TrackTaggedEvent
import com.example.tag_service.domain.model.Tag
import org.slf4j.LoggerFactory

class AssignTagsCommandHandler(
    private val repository: TagRepository,
    private val eventPublisher: EventPublisher
) : CommandHandler<AssignTagsCommand, Unit> {

    private val logger = LoggerFactory.getLogger(AssignTagsCommandHandler::class.java)

    override fun handle(command: AssignTagsCommand) {
        val trackId = command.trackId
        val keywords = command.keywords

        logger.info("Executing AssignTagsUseCase for trackId=$trackId with keywords=$keywords")

        val upperKeywords = keywords.map { it.lowercase() }

        val tagsToAssign: List<Tag> = repository.getTagsByNames(upperKeywords)

        logger.info("Found ${tagsToAssign.size} matching tags in the repository")

        if (!tagsToAssign.isEmpty()) {

            logger.debug("Assigning tags to trackId=$trackId: ${tagsToAssign.map { it.name }}")
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