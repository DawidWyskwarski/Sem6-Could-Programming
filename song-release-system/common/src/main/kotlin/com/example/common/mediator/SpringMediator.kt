package com.example.common.mediator

import com.example.common.commands.Command
import com.example.common.commands.CommandHandler
import com.example.common.queries.Query
import com.example.common.queries.QueryHandler
import org.springframework.core.ResolvableType
import org.springframework.stereotype.Component

@Component
class SpringMediator(
    commandHandlers: List<CommandHandler<*, *>>,
    queryHandlers: List<QueryHandler<*, *>>
) : Mediator {

    private val commandHandlerMap: Map<Class<*>, CommandHandler<*, *>> =
        commandHandlers.associateBy { handler ->
            ResolvableType.forClass(handler::class.java)
                .`as`(CommandHandler::class.java)
                .getGeneric(0)
                .resolve()!!
        }

    private val queryHandlerMap: Map<Class<*>, QueryHandler<*, *>> =
        queryHandlers.associateBy { handler ->
            ResolvableType.forClass(handler::class.java)
                .`as`(QueryHandler::class.java)
                .getGeneric(0)
                .resolve()!!
        }

    @Suppress("UNCHECKED_CAST")
    override fun <R> send(command: Command<R>): R {
        val handler = commandHandlerMap[command::class.java] as? CommandHandler<Command<R>, R>
            ?: error("No handler for ${command::class.simpleName} found")
        return handler.handle(command)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <R> send(query: Query<R>): R {
        val handler = queryHandlerMap[query::class.java] as? QueryHandler<Query<R>, R>
            ?: error("No handler for ${query::class.simpleName} found")
        return handler.handle(query)
    }
}