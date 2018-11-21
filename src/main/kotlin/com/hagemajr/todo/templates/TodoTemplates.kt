package com.hagemajr.todo.templates

import io.ktor.html.*
import kotlinx.html.HTML
import kotlinx.html.*


class MulticolumnTemplate(val main: MainTemplate = MainTemplate()) : Template<HTML> {
    val column1 = Placeholder<FlowContent>()
    val column2 = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(main) {
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
        }
    }
}

class MainTemplate : Template<HTML> {
    val content = Placeholder<HtmlBlockTag>()
    val menu = TemplatePlaceholder<MenuTemplate>()
    override fun HTML.apply() {
        head {
            title { +"Template" }
            link(rel = "stylesheet", href="/static/bulma.min.css")
        }
        body {
            div(classes = "container"){
                section(classes = "hero is-info is-bold is-rounded"){
                    div(classes = "hero-body"){
                        div("container"){
                            h1("title"){
                                +"Todo List"
                            }
                            h2("subtitle"){
                                +"My Todo Implementation"
                            }
                        }
                    }
                }
            }
            h1 {
                insert(content)
            }
            insert(MenuTemplate(), menu)
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