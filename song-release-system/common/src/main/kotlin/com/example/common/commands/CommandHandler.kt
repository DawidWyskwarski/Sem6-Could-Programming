package com.example.common.commands

interface CommandHandler<in C : Command<R>, out R> {
    fun handle(command: C): R
}