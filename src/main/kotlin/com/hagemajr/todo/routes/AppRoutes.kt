package com.hagemajr.todo.routes

import com.hagemajr.pebble4k.PebbleContent
import com.hagemajr.todo.models.TodoDAO
import com.hagemajr.todo.templates.MainTemplate
import com.hagemajr.todo.templates.MulticolumnTemplate
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.stream.createHTML
import java.util.HashMap

fun Routing.root() {
    val output = "hello"

    get("/") {
        call.respondText(output, ContentType.Text.Plain)
    }
}

fun Routing.demo2(dao : TodoDAO) {
    get("/demo") {
        val context = HashMap<String, Any>()
        context["websiteTitle"] = "My First Website"
        call.respond(PebbleContent("HelloWorld.peb", context, "e"))
    }
}

fun Routing.demo3(dao : TodoDAO) {
    get("/demo2") {
        call.respondHtmlTemplate(MulticolumnTemplate()) {
            column1 {
                for(x in dao.getTodos()) {
                    +x.name
                }
            }
            column2 {
                for(x in dao.getTodos()) {
                    +x.notes
                }
            }
        }
    }
}
