package com.hagemajr.todo.routes

import com.hagemajr.pebble4k.PebbleContent
import com.hagemajr.todo.models.TodoDAO
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import java.util.HashMap

fun Routing.apiRoot() {
    val output = "hello"

    get("/api") {
        call.respondText(output, ContentType.Text.Plain)
    }
}

fun Routing.apiDemo(dao : TodoDAO) {
    get("/api/demo") {
        val context = HashMap<String, Any>()
        context["websiteTitle"] = "My First Website"
        context["content"] = "My Interesting Content"
        context["todos"] = dao.getTodos()
        call.respond(PebbleContent("HelloWorld.peb", context, "e"))
    }
}

