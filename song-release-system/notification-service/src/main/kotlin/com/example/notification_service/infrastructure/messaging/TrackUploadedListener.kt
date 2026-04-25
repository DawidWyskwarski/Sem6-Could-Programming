package com.example.notification_service.infrastructure.messaging

import com.example.notification_service.application.usecases.NotifyFollowersUseCase
import com.example.notification_service.domain.event.incoming.TrackUploadedEvent
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.ExchangeTypes

/**
 * Listener for handling TrackUploadedEvent messages from RabbitMQ.
 * This class listens to the "TrackUploadedEvent" exchange and processes incoming events by invoking the NotifyFollowersUseCase.
 */
@Component
class TrackUploadedListener(
    private val notifyFollowersUseCase: NotifyFollowersUseCase
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
         logger.info("Received TrackUploadedEvent for trackId: {}, artistId: {}, title: '{}'", event.trackId, event.artistId, event.title)

         notifyFollowersUseCase.execute(
             artistId = event.artistId,
             trackId = event.trackId,
             trackTitle = event.title
         )

         logger.info("Successfully processed TrackUploadedEvent for trackId: {}", event.trackId)
     }
}