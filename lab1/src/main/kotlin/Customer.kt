package org.example

import dev.kourier.amqp.connection.AMQPConnection
import kotlin.reflect.KClass

interface ICustomer {
    suspend fun receive()
}

/**
 * Generic type for customers
 * It connects to the queue dictated by the [recEvent] and waits for any event.
 * Then it just logs it.
 *
 * @param recEvent Generic [Event] type that dictates which type shall be received
 * @param name [AMQPCoordinator.name]
 * @param connection [AMQPCoordinator.connection]
 * @param receiveEvent see [Publisher.publishEventClass] it is analogous.
 */
class Customer<recEvent : Event>(
    name: String,
    connection: AMQPConnection,
    private val receiveEvent: KClass<recEvent>
) : AMQPCoordinator(name, connection), ICustomer {

    /**
     * Using the [AMQPCoordinator.withChannel] as a trailing lambda.
     * Code inside this function will be executed in the try block from [AMQPCoordinator.withChannel]
     *
     * It just 'connects' to the proper queue and listens (So sneaky)
     */
    override suspend fun receive() = withChannel { channel ->
        logger.info("Method receive() started for event type: {}", receiveEvent.simpleName)

        val eventName = channel.declareQueueFor(receiveEvent)

        val consumer = channel.basicConsume(eventName, noAck = true)

        // Waiting...
        for (delivery in consumer) {
            val message = delivery.message.body.decodeToString()
            logger.info("Received message: {}", message)
        }
    }
}

// Factory function
// We use an inline function with a reified type parameter so the generic type is not erased at runtime.
// This allows us to pass T::class to the constructor without requiring the caller to pass it explicitly.
inline fun <reified recEvent : Event> createCustomer(
    name: String,
    connection: AMQPConnection,
): Customer<recEvent> {
    return Customer(
        name = name,
        connection = connection,
        receiveEvent = recEvent::class
    )
}