package com.example.playlist_service.infrastructure.messaging

import com.example.playlist_service.application.usecases.AddToPlaylistUseCase
import com.example.playlist_service.domain.event.incoming.TrackTaggedEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

/**
 * Listener for handling TrackTaggedEvent messages from RabbitMQ.
 * This class listens to the "TrackTaggedEvent" exchange and processes incoming events by invoking the AddToPlaylistUseCase.
 */
@Component
class TrackTaggedListener(
    private val addToPlaylistUseCase: AddToPlaylistUseCase
) {

    private val logger = LoggerFactory.getLogger(TrackTaggedListener::class.java)

    @RabbitListener(
        bindings = [
            QueueBinding(
                value = Queue(value = "playlist-service.TrackTaggedEvent", durable = "true"),
                exchange = Exchange(value = "TrackTaggedEvent", type = ExchangeTypes.FANOUT)
            )
        ]
    )
    fun handleTrackTaggedEvent(event: TrackTaggedEvent) {
        logger.info("Received TrackTaggedEvent for trackId={} with {} tags", event.trackId, event.tagIds.size)

        addToPlaylistUseCase.execute(event.trackId, event.tagIds)

        logger.info("Successfully processed TrackTaggedEvent for trackId={}", event.trackId)
    }
}