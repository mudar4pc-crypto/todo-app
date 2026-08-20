package com.mudar4pc.todoapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TodoRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("TodoApp", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val todoListKey = "todo_list"

    fun getAllTodos(): List<TodoItem> {
        val json = sharedPreferences.getString(todoListKey, null)
        return if (json != null) {
            val type = object : TypeToken<List<TodoItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addTodo(todo: TodoItem) {
        val todos = getAllTodos().toMutableList()
        todos.add(todo)
        saveTodos(todos)
    }

    fun updateTodo(updatedTodo: TodoItem) {
        val todos = getAllTodos().toMutableList()
        val index = todos.indexOfFirst { it.id == updatedTodo.id }
        if (index != -1) {
            todos[index] = updatedTodo
            saveTodos(todos)
        }
    }

    fun deleteTodo(id: String) {
        val todos = getAllTodos().filter { it.id != id }
        saveTodos(todos)
    }

    fun toggleTodoCompletion(id: String) {
        val todos = getAllTodos().toMutableList()
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            todos[index] = todos[index].copy(isCompleted = !todos[index].isCompleted)
            saveTodos(todos)
        }
    }

    private fun saveTodos(todos: List<TodoItem>) {
        val json = gson.toJson(todos)
        sharedPreferences.edit().putString(todoListKey, json).apply()
    }
}