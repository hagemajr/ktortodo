package com.hagemajr.todo

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.hagemajr.pebble4k.*
import com.hagemajr.todo.models.TodoDAO
import com.hagemajr.todo.routes.root as rootRoutes
import com.hagemajr.todo.routes.demo2
import com.hagemajr.todo.routes.demo3
import io.ktor.http.content.resources
import io.ktor.http.content.static


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::mainModule).start(wait = true)
}

fun Application.mainModule() {
    install(Pebble) {
        templatePath = "templates/"
        strictVariables = true
    }

    val dao = TodoDAO

    dao.init()

    routing {
        rootRoutes()
        demo2(dao)
        demo3(dao)
        static("static") {
            resources("static/css")
            resources("static/js")
        }
    }
}

