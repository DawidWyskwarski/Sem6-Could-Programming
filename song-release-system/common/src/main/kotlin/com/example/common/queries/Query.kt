package com.example.common.queries

import java.time.Instant

interface Query<out R> {
    val timestamp: Instant
}