package org.example

import dev.kourier.amqp.connection.AMQPConnection
import kotlin.reflect.KClass

interface IPublisher {
    suspend fun publish()
}

/**
 * Generic class for publishers.
 * It is parameterized so that every publisher in the exercise can use this.
 *
 * @param pubEvent Generic [Event] type that dictates which type shall be published
 * @param name [AMQPCoordinator.name]
 * @param connection [AMQPCoordinator.connection]
 * @param publishEventClass Also generic [Event] type for the same purpose but for some reason I can't just pass in int the <>. That's the reason why i made the factory function
 * @param delayStrategy Suspended function that ultimately just waits. But it can do so in different ways :)
 * @param eventGenerator Function that returns us an event based on the payload we specify
 */
class Publisher<pubEvent : Event>(
    name: String,
    connection: AMQPConnection,
    private val publishEventClass: KClass<pubEvent>,
    private val delayStrategy: suspend () -> Unit,
    private val eventGenerator: (payload: String) -> pubEvent
) : AMQPCoordinator(name, connection), IPublisher {

    /**
     * Using the [AMQPCoordinator.withChannel] as a trailing lambda.
     * Code inside this function will be executed in the try block from [AMQPCoordinator.withChannel]
     *
     * Other than that it is just an infinite loop that use [delayStrategy]
     * to wait in many different ways to then publish an event and repeat everything again.
     */
    override suspend fun publish() = withChannel { channel ->
        logger.info("Method publish() started for event type: {}", publishEventClass.simpleName)

        // Usage of the extension function from AMQP class.
        // :O So elegant :O
        val eventName = channel.declareQueueFor(publishEventClass)

        while (true) {
            delayStrategy()

            val pubEvent = eventGenerator(
                "Hello world from $name!",
            )

            channel.basicPublish(
                exchange = "",
                routingKey = eventName,
                // convert event to byteArray,
                // should be done with serialization or something, but I am lazy,
                // and we don't do anything with these event in consumers anyway
                body = pubEvent.toString().toByteArray(),
            )

            logger.info("Published: $pubEvent")
        }
    }
}

// Factory function
// We use an inline function with a reified type parameter so the generic type is not erased at runtime.
// This allows us to pass T::class to the constructor without requiring the caller to pass it explicitly.
inline fun <reified pubEvent : Event> createPublisher(
    name: String,
    connection: AMQPConnection,
    noinline delayStrategy: suspend () -> Unit,
    noinline eventGenerator: (payload: String) -> pubEvent
): Publisher<pubEvent> {
    return Publisher(
        name = name,
        connection = connection,
        publishEventClass = pubEvent::class,
        delayStrategy = delayStrategy,
        eventGenerator = eventGenerator
    )
}