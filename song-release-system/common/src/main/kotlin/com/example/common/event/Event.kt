package com.example.common.event

import java.time.Instant

/**
 * Generic event interface that all events will implement. It contains a timestamp to indicate when the event occurred.
 */
interface Event {
    val timestamp: Instant
}