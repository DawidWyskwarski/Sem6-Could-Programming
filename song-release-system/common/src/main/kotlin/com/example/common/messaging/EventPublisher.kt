package com.example.common.messaging

import com.example.common.event.Event

/**
 * An interface used in application layers of the services.
 * By defining a plain (no spring boot nor other libraries) interface lower layers aren't coupled to the implementation of the event publisher,
 * allowing for better separation of concerns and easier testing (if any exist XD).
 */
interface EventPublisher {
    fun publish(event: Event)
}