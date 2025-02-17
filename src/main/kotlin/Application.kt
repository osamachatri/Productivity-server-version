package com.oussama_chatri

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit {
    io.ktor.server.netty.EngineMain.main(args)
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
    }.start(true)
}


fun Application.module() {

//    configureSecurity()
//    install(ContentNegotiation) {
//        json()
//    }
//
//    DatabaseFactory.db
//
//    routing {
//        get("/") {
//            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
//        }
//        authRoutes()
//        notesRoutes()
//    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
    }

}