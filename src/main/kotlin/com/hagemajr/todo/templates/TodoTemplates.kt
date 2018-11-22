package com.hagemajr.todo.templates

import com.hagemajr.todo.models.Todo
import io.ktor.html.*
import kotlinx.html.HTML
import kotlinx.html.*


class MulticolumnTemplate(val main: MainTemplate = MainTemplate()) : Template<HTML> {

    val todoList = listOf(
        Todo(name = "Todo1", complete = false, notes = ""),
        Todo(name = "Todo2", complete = false, notes = ""),
        Todo(name = "Todo3", complete = false, notes = "")
    )

    override fun HTML.apply() {
        insert(main) {
            header {
                headerTitle = "Overridden title"
            }
            appBody {
                todoItems{
                    todos = todoList
                }
            }
            // Only need to include if overriding defaults
            //footer {
            //  footerContent { +"A POC by John Hageman" }
            //}
        }
    }
}

class MainTemplate : Template<HTML> {
    val header = TemplatePlaceholder<HeaderTemplate>()
    val appBody = TemplatePlaceholder<BodyTemplate>()
    val footer = TemplatePlaceholder<FooterTemplate>()
    override fun HTML.apply() {
        head {
            title { +"Template" }
            link(rel = "stylesheet", href = "/static/bulma.min.css")
        }
        body {
            div(classes = "container") {
                insert(HeaderTemplate(), header)
                insert(BodyTemplate(), appBody)
                insert(FooterTemplate(), footer)
            }
        }
    }
}

class HeaderTemplate : Template<FlowContent> {
    var headerTitle = "ToDo List"
    var headerSubtitle = "My Todo Implementation"
    override fun FlowContent.apply() {

        section(classes = "hero is-info is-bold is-rounded") {
            div(classes = "hero-body") {
                div("container") {
                    h1("title") {
                        +headerTitle
                    }
                    h2("subtitle") {
                        +headerSubtitle
                    }
                }
            }

        }
    }
}

class BodyTemplate : Template<FlowContent>{
    private val inputTemplate = TemplatePlaceholder<InputTemplate>()
    val todoItems = TemplatePlaceholder<TodoItemListTemplate>()
    override fun FlowContent.apply(){
        div(classes = "columns is-centered"){
            div(classes = "column is-half"){
                div(classes = "section"){
                    insert(InputTemplate(), inputTemplate)
                    div(classes = "tabs is-centered"){
                        ul{
                            li(classes = "is-active"){
                                a { +"Not Done" }
                            }
                            li{
                                a { +"Done"}
                            }
                        }
                    }
                    insert(TodoItemListTemplate(), todoItems)
                }
            }
        }
    }
}

class TodoItemListTemplate : Template<FlowContent>{
    var todos = listOf<Todo>()

    override fun FlowContent.apply() {
        todos.forEach {
            div(classes = "box") {
                div(classes = "field is-grouped") {
                    p(classes = "control is-expanded") {
                        label(classes = "label") {
                            +it.name
                        }
                    }
                    p(classes = "control") {
                        a(classes = "button is-info") {
                            +"Edit"
                        }
                    }
                    p(classes = "control") {
                        a(classes = "button is-danger") {
                            +"Complete"
                        }
                    }
                }
            }
        }
    }
}

class InputTemplate : Template<FlowContent>{
    override fun FlowContent.apply(){
        div(classes = "field has-addons"){
            p(classes = "control is-expanded"){
                input(classes = "input", type = InputType.text){
                    placeholder = "What do you need to do?"
                }
            }
            p(classes = "control"){
                a(classes = "button is-info"){
                    +"Add Todo"
                }
            }
        }
    }
}

class FooterTemplate : Template<FlowContent>{
    val footerContent = "A POC by John Hageman"
    override fun FlowContent.apply(){
        footer(classes = "footer") {
            div(classes = "content has-text-centered"){
                p {
                    +footerContent
                }
            }
        }
    }
}