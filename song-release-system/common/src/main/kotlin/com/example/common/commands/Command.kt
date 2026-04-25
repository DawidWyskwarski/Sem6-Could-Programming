package com.example.common.commands

import java.time.Instant

interface Command<out R> {
    val timestamp: Instant
}