package com.example.common.messaging

import com.example.common.event.Event
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory

/**
 * Component implementing the [EventPublisher] interface, utilizing RabbitMQ as the underlying messaging broker.
 *
 * This publisher assumes the use of fanout exchanges where the target exchange name
 * exactly matches the simple class name of the published event.
 */
@Component
class RabbitMQEventPublisher(
    private val rabbitTemplate: RabbitTemplate
) : EventPublisher {

    private val logger = LoggerFactory.getLogger(RabbitMQEventPublisher::class.java)

    /**
     * Publishes a domain [Event] to the RabbitMQ broker.
     */
    override fun publish(event: Event) {

        val eventName = event.javaClass.simpleName
        logger.info("Publishing event: {}", eventName)

        // The exchange name is the same as the event name, and the routing key is empty since we're using a fanout exchange.
        rabbitTemplate.convertAndSend("$eventName", "", event)
    }
}