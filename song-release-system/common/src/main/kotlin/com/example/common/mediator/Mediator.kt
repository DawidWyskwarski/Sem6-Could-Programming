package com.example.common.mediator

import com.example.common.commands.Command
import com.example.common.queries.Query

interface Mediator {
    fun <R> send(query: Query<R>): R
    fun <R> send(command: Command<R>): R
}