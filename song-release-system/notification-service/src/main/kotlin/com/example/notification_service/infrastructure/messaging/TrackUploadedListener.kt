package com.example.notification_service.infrastructure.messaging

import com.example.common.mediator.Mediator
import com.example.notification_service.domain.commands.NotifyFollowersCommand
import com.example.notification_service.domain.event.incoming.TrackUploadedEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

/**
 * Listener for handling TrackUploadedEvent messages from RabbitMQ.
 * This class listens to the "TrackUploadedEvent" exchange and processes incoming events by invoking the NotifyFollowersUseCase.
 */
@Component
class TrackUploadedListener(
    private val mediator: Mediator
) {

     private val logger = LoggerFactory.getLogger(TrackUploadedListener::class.java)

     @RabbitListener(
         bindings = [
             QueueBinding(
                 value = Queue(value = "notification-service.TrackUploadedEvent", durable = "true"),
                 exchange = Exchange(value = "TrackUploadedEvent", type = ExchangeTypes.FANOUT)
             )
         ]
     )
     fun handleTrackUploadedEvent(event: TrackUploadedEvent) {
         logger.info("Received TrackUploadedEvent for trackId: ${event.trackId}, artistId: ${event.artistId}, title: '${event.title}'")

         mediator.send(
             NotifyFollowersCommand(
                 artistId = event.artistId,
                 trackId = event.trackId,
                 trackTitle = event.title
             )
         )

         logger.info("Successfully processed TrackUploadedEvent for trackId: ${event.trackId}")
     }
}