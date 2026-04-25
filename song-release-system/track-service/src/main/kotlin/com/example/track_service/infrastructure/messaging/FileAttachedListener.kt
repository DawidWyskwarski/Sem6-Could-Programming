package com.example.track_service.infrastructure.messaging

import com.example.common.mediator.Mediator
import com.example.track_service.domain.commands.MarkTrackAsUploadedCommand
import com.example.track_service.domain.event.incoming.FileAttachedEvent
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.QueueBinding

/**
 * Listener for handling FileAttachedEvent messages from RabbitMQ.
 * This class listens to the "FileAttachedEvent" exchange and processes incoming events by invoking the OnFileAttachedUseCase.
 */
@Component
class FileAttachedListener(
    private val mediator: Mediator
) {

    private val logger = LoggerFactory.getLogger(FileAttachedListener::class.java)

    @RabbitListener(
        bindings = [
            QueueBinding(
                value = Queue(value = "track-service.FileAttachedEvent", durable = "true"),
                exchange = Exchange(value = "FileAttachedEvent", type = ExchangeTypes.FANOUT)
            )
        ]
    )
    fun handleFileAttachedEvent(event: FileAttachedEvent) {
        logger.info("Received FileAttachedEvent for trackId={}", event.trackId)

        mediator.send(
            MarkTrackAsUploadedCommand(event.trackId)
        )

        logger.info("Successfully processed FileAttachedEvent for trackId={}", event.trackId)
    }
}