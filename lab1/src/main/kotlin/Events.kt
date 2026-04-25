package org.example

interface Event

data class Type1Event(
    val timestamp: Long,
    val payload: String
): Event

data class Type2Event(
    val timestamp: Long,
    val payload: String
): Event

data class Type3Event(
    val timestamp: Long,
    val payload: String
): Event

data class Type4Event(
    val timestamp: Long,
    val payload: String
): Event
