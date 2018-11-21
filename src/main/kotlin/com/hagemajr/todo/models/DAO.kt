package com.hagemajr.todo.models

import com.hagemajr.todo.models.Todo


object TodoDAO {
    var TodoDB : MutableList<Todo> = mutableListOf()

    fun addTodo(t : Todo) : Unit {
        TodoDB.add((t))
    }

    fun init() : Unit {
        this.TodoDB = mutableListOf(Todo(name = "Test", notes = "Test Notes", complete = false), Todo(name = "Test2", notes = "Test Notes2", complete = false))
    }

    fun getTodos() : List<Todo> {
        return TodoDB.toList()
    }
}
