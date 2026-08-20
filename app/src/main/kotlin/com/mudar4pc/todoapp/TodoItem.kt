package com.mudar4pc.todoapp

import java.io.Serializable

data class TodoItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val dueDate: Long? = null
) : Serializable