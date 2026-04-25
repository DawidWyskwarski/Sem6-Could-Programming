package org.example


import dev.kourier.amqp.channel.AMQPChannel
import dev.kourier.amqp.connection.AMQPConnection
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass


/**
 * Class that abstracts the flow of the Publisher/Customer to avoid code duplication.
 *
 * @param name allows the distinction of the actors of the process
 * @param connection allows all entities to use one and the same connection but different channels
 */
abstract class AMQPCoordinator(
    val name: String,
    private val connection: AMQPConnection,
) {
    /**
     * Logger with the that sets its name to [javaClass.simpleName-name] e.g. [Publisher-StaticPublisher]
     */
    protected val logger: Logger = LoggerFactory.getLogger("${javaClass.simpleName}-$name")

    /**
     * Main function that dictates  and abstracts the flow of Publisher/Customer.
     *
     * @param work a suspended function that shall be used in the try-catch block.
     */
    protected suspend fun withChannel(work: suspend (AMQPChannel) -> Unit) {
        val channel = connection.openChannel()

        try {
            work(channel)
        }catch (e: Exception) {
            logger.error("{} encountered an error: {}", name, e.message)
        } finally {
            channel.close()
            logger.info("{} channel closed", name)
        }
    }

    /**
     *  Helper extension function for `AMQPChannel` that declares queue for the actor based on the `Event` class name using reflection.
     *
     *  @param eventClass Class of the event the Publisher/Customer shall publish/receive
     *  @return name the of the queue that it just declared
     */
    protected suspend fun AMQPChannel.declareQueueFor(eventClass: KClass<*>): String {
        val queueName = eventClass.simpleName!!

        queueDeclare(
            name = queueName,
            durable = false,
            exclusive = false,
            autoDelete = false,
            arguments = emptyMap()
        )

        return queueName
    }

}