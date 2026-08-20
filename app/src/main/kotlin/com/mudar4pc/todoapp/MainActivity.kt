package com.mudar4pc.todoapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mudar4pc.todoapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: TodoRepository
    private lateinit var adapter: TodoAdapter
    private var editingTodoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = TodoRepository(this)
        setupAdapter()
        setupListeners()
        loadTodos()
    }

    private fun setupAdapter() {
        adapter = TodoAdapter(
            todos = repository.getAllTodos().toMutableList(),
            onToggleComplete = { id ->
                repository.toggleTodoCompletion(id)
                loadTodos()
            },
            onEdit = { todo ->
                showEditDialog(todo)
            },
            onDelete = { id ->
                repository.deleteTodo(id)
                loadTodos()
                Toast.makeText(this, "Todo deleted", Toast.LENGTH_SHORT).show()
            }
        )
        binding.recyclerViewTodos.adapter = adapter
    }

    private fun setupListeners() {
        binding.buttonAddTodo.setOnClickListener {
            showAddDialog()
        }

        binding.buttonClearCompleted.setOnClickListener {
            clearCompletedTodos()
        }
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Todo")

        val input = EditText(this).apply {
            hint = "Enter todo title"
        }
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            val title = input.text.toString().trim()
            if (title.isNotEmpty()) {
                val newTodo = TodoItem(title = title)
                repository.addTodo(newTodo)
                loadTodos()
                Toast.makeText(this, "Todo added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun showEditDialog(todo: TodoItem) {
        editingTodoId = todo.id
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Todo")

        val input = EditText(this).apply {
            setText(todo.title)
            hint = "Enter todo title"
        }
        builder.setView(input)

        builder.setPositiveButton("Update") { _, _ ->
            val newTitle = input.text.toString().trim()
            if (newTitle.isNotEmpty()) {
                val updatedTodo = todo.copy(title = newTitle)
                repository.updateTodo(updatedTodo)
                loadTodos()
                Toast.makeText(this, "Todo updated", Toast.LENGTH_SHORT).show()
            }
            editingTodoId = null
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            editingTodoId = null
        }
        builder.show()
    }

    private fun clearCompletedTodos() {
        val todos = repository.getAllTodos()
        val completedTodos = todos.filter { it.isCompleted }

        if (completedTodos.isEmpty()) {
            Toast.makeText(this, "No completed todos to clear", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Clear Completed")
        builder.setMessage("Delete ${completedTodos.size} completed todo(s)?")
        builder.setPositiveButton("Yes") { _, _ ->
            completedTodos.forEach { repository.deleteTodo(it.id) }
            loadTodos()
            Toast.makeText(this, "Completed todos cleared", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }

    private fun loadTodos() {
        val todos = repository.getAllTodos().sortedByDescending { it.createdAt }
        adapter.updateList(todos)
        updateTodoStats()
    }

    private fun updateTodoStats() {
        val todos = repository.getAllTodos()
        val completedCount = todos.count { it.isCompleted }
        val totalCount = todos.size
        binding.textViewStats.text = "$completedCount / $totalCount completed"
    }
}