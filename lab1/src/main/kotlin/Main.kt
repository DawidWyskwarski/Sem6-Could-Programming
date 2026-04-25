package org.example

import dev.kourier.amqp.connection.AMQPConnection
import dev.kourier.amqp.connection.amqpConfig
import dev.kourier.amqp.connection.createAMQPConnection
import kotlinx.coroutines.*
import kotlin.random.Random

const val CONNECTION_STRING = "BROKER_URL"

/**
 * Time of delay between publishing new message by the static publishers
 */
const val STATIC_DELAY = 10000L

// = runBlocking allows app to run concurrently
fun main(): Unit = runBlocking {

    // Making a AMQP connection
    // All Publishers/Consumers use this one connection
    // From what i know making a connection is a heavy process to do
    // Better to do this once than multiple times
    val config = amqpConfig(CONNECTION_STRING)
    val connection = createAMQPConnection(
        coroutineScope = this,
        config = config
    )

    try {
        runSystem(connection)
    } finally {
        connection.close()
    }
}

suspend fun runSystem(connection: AMQPConnection) = coroutineScope {

    // Launch 3 static type 1 event publishers
    for (i in 1..3) {
        launch {
            createPublisher<Type1Event>(
                name = "Static-Publisher$i",
                connection = connection,
                delayStrategy = { delay(STATIC_DELAY) },
                eventGenerator = { payload ->
                    Type1Event(System.currentTimeMillis(), payload)
                }
            ).publish()
        }
    }

    // Launch random type 2 event publisher
    launch {
        createPublisher<Type2Event>(
            name = "Random-Publisher4",
            connection = connection,
            delayStrategy = { delay(Random.nextLong(from = 10L, until = 15L)) },
            eventGenerator = { payload ->
                Type2Event(System.currentTimeMillis(), payload)
            }
        ).publish()
    }

    // Launch random type 3 event publisher
    launch {
        createPublisher<Type3Event>(
            name = "Random-Publisher5",
            connection = connection,
            delayStrategy = { delay(Random.nextLong(from = 11L, until = 18L) ) },
            eventGenerator = { payload ->
                Type3Event(System.currentTimeMillis(), payload)
            }
        ).publish()
    }

    // Launch 2 Customers for Type 1
    for (i in 1..2) {
        launch {
            createCustomer<Type1Event>(
                name = "Customer$i",
                connection = connection
            ).receive()
        }
    }

    // Launch Customer for Type 2
    launch {
        createCustomer<Type2Event>(
            name = "Customer3",
            connection = connection
        ).receive()
    }

    // Launch Customer for Type 3 and Publisher for Type 4
    launch {
        createCustomerPublisher<Type3Event, Type4Event>(
            name = "CustomerPublisher4",
            connection = connection,
            pubEventGenerator = { payload ->
                Type4Event(System.currentTimeMillis(), payload)
            }
        ).receive()
    }

    // Launch Customer for type 4
    launch {
        createCustomer<Type4Event>(
            name = "Customer5",
            connection = connection
        ).receive()
    }
}