package org.example

import dev.kourier.amqp.connection.AMQPConnection
import kotlin.reflect.KClass

/**
 * Generic class that takes 2 generic arguments [recEvent] and [pubEvent]
 * It listens for the [recEvent] and if one is received publishes the [pubEvent]
 * This class is a hybrid of Customer and Publisher
 *
 * @param recEvent Generic [Event] type that dictates which type shall be received
 * @param pubEvent Generic [Event] type that dictates which type shall be published
 * @param name [AMQPCoordinator.name]
 * @param connection [AMQPCoordinator.connection]
 * @param publishEventClass [Publisher.publishEventClass]
 * @param receiveEventClass [Customer.receiveEvent]
 * @param pubEventGenerator [Publisher.eventGenerator]
 */
class CustomerPublisher<recEvent: Event, pubEvent: Event>(
    name: String,
    connection: AMQPConnection,
    private val receiveEventClass: KClass<recEvent>,
    private val publishEventClass: KClass<pubEvent>,
    private val pubEventGenerator: (payload: String) -> pubEvent
): AMQPCoordinator(name, connection), ICustomer {

    override suspend fun receive() = withChannel { channel ->

        logger.info("Method receive() started for event type: {}", receiveEventClass.simpleName)

        // Connection to queues for both publishing and receiving
        val receiveEventName = channel.declareQueueFor(receiveEventClass)
        val publishEventName = channel.declareQueueFor(publishEventClass)

        val consumer = channel.basicConsume(receiveEventName, noAck = true)

        for (delivery in consumer) {
            val message = delivery.message.body.decodeToString()
            logger.info("Received message: {}", message)

            // If the event is received we generate and publish a new one
            val pubEvent = pubEventGenerator(
                "Hello world from $name",
            )

            channel.basicPublish(
                exchange = "",
                routingKey = publishEventName,
                body = pubEvent.toString().toByteArray(),
            )
            logger.info("Published: {}", pubEvent)
        }
    }
}

// Check comments for createPublisher and createCustomer.
// This one just has 2 generic types.
inline fun <reified recEvent : Event, reified pubEvent: Event> createCustomerPublisher(
    name: String,
    connection: AMQPConnection,
    noinline pubEventGenerator: (payload: String) -> pubEvent
): CustomerPublisher<recEvent, pubEvent> {
    return CustomerPublisher(
        name = name,
        connection = connection,
        receiveEventClass = recEvent::class,
        publishEventClass = pubEvent::class,
        pubEventGenerator = pubEventGenerator,
    )
}