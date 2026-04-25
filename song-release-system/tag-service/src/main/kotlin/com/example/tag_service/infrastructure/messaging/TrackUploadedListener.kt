package com.example.tag_service.infrastructure.messaging

import com.example.tag_service.application.usecases.AssignTagsUseCase
import com.example.tag_service.domain.event.incoming.TrackUploadedEvent
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.ExchangeTypes

/**
 * Listener for handling TrackUploadedEvent messages from RabbitMQ.
 * This class listens to the "TrackUploadedEvent" exchange and processes incoming events by invoking the AssignTagsUseCase.
 */
@Component
class TrackUploadedListener(
    private val assignTagsUseCase: AssignTagsUseCase,
) {

    private val logger = LoggerFactory.getLogger(TrackUploadedListener::class.java)

    @RabbitListener(
        bindings = [
            QueueBinding(
                value = Queue(value = "tag-service.TrackUploadedEvent", durable = "true"),
                exchange = Exchange(value = "TrackUploadedEvent", type = ExchangeTypes.FANOUT)
            )
        ]
    )
    fun handleTrackUploadedEvent(event: TrackUploadedEvent) {
        logger.info("Received TrackUploadedEvent for trackId: {} with keywords: {}", event.trackId, event.keywords)

        assignTagsUseCase.execute(event.trackId, event.keywords)

        logger.info("Successfully processed TrackUploadedEvent for trackId: {}", event.trackId)
    }
}