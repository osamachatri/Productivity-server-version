package com.oussama_chatri

import com.oussama_chatri.Api.routes.authRoutes
import com.oussama_chatri.Api.routes.notesRoutes
import com.oussama_chatri.Api.routes.toDoListsRoutes
import com.oussama_chatri.security.configureSecurity
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        module()
    }.start(true)
}


fun Application.module() {

    configureSecurity()
    install(ContentNegotiation) {
        json()
    }

    DatabaseFactory.db

    routing {
        authRoutes()
        notesRoutes()
        toDoListsRoutes()

    }
}
