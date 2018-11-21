package com.hagemajr.todo.templates

import io.ktor.html.*
import kotlinx.html.HTML
import kotlinx.html.*
import org.intellij.lang.annotations.Flow


class MulticolumnTemplate(val main: MainTemplate = MainTemplate()) : Template<HTML> {
    val column1 = Placeholder<FlowContent>()
    val column2 = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(main) {
            header {
                headerTitle { +"ToDo List"}
                headerSubtitle { +"My Todo Implementation"}
            }
            menu {
                item { +"One" }
                item { +"Two" }
            }
            content {
                div("column") {
                    insert(column1)
                }
                div("column") {
                    insert(column2)
                }
            }
            footer {
                footerContent { +"A POC by John Hageman" }
            }
        }
    }
}

class MainTemplate : Template<HTML> {
    val header = TemplatePlaceholder<HeaderTemplate>()
    val content = Placeholder<HtmlBlockTag>()
    val appBody = TemplatePlaceholder<BodyTemplate>()
    val menu = TemplatePlaceholder<MenuTemplate>()
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
                h1 {
                    insert(content)
                }
                insert(MenuTemplate(), menu)
                insert(FooterTemplate(), footer)
            }
        }
    }
}

class HeaderTemplate : Template<FlowContent> {
    val headerTitle = Placeholder<HtmlBlockTag>()
    val headerSubtitle = Placeholder<HtmlBlockTag>()
    override fun FlowContent.apply() {

        section(classes = "hero is-info is-bold is-rounded") {
            div(classes = "hero-body") {
                div("container") {
                    h1("title") {
                        insert(headerTitle)
                    }
                    h2("subtitle") {
                        insert(headerSubtitle)
                    }
                }
            }

        }
    }
}

class BodyTemplate : Template<FlowContent>{
    val inputTemplate = TemplatePlaceholder<InputTemplate>()
    override fun FlowContent.apply(){
        div(classes = "columns is-centered"){
            div(classes = "column is-half"){
                div(classes = "section"){
                    insert(InputTemplate(), inputTemplate)
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
    val footerContent = Placeholder<HtmlBlockTag>()
    override fun FlowContent.apply(){
        footer(classes = "footer") {
            div(classes = "content has-text-centered"){
                p {
                    insert(footerContent)
                }
            }
        }
    }
}


class MenuTemplate : Template<FlowContent> {
    val item = PlaceholderList<UL, FlowContent>()
    override fun FlowContent.apply() {
        if (!item.isEmpty()) {
            ul {
                each(item) {
                    li {
                        if (it.first) b {
                            insert(it)
                        } else {
                            insert(it)
                        }
                    }
                }
            }
        }
    }
}