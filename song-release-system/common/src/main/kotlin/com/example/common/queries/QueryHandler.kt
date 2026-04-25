package com.example.common.queries

interface QueryHandler<in Q: Query<R>, out R> {
    fun handle(query: Q): R
}