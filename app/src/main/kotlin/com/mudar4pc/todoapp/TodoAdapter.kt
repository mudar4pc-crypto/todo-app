package com.mudar4pc.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mudar4pc.todoapp.databinding.TodoItemBinding

class TodoAdapter(
    private val todos: MutableList<TodoItem>,
    private val onToggleComplete: (String) -> Unit,
    private val onEdit: (TodoItem) -> Unit,
    private val onDelete: (String) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: TodoItem) {
            binding.apply {
                todoTitle.text = todo.title
                todoTitle.paint.isStrikeThruText = todo.isCompleted

                todoDescription.text = if (todo.description.isNotEmpty()) todo.description else "No description"
                todoDescription.paint.isStrikeThruText = todo.isCompleted

                checkboxComplete.isChecked = todo.isCompleted
                checkboxComplete.setOnCheckedChangeListener { _, _ ->
                    onToggleComplete(todo.id)
                }

                buttonEdit.setOnClickListener {
                    onEdit(todo)
                }

                buttonDelete.setOnClickListener {
                    onDelete(todo.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount() = todos.size

    fun updateList(newTodos: List<TodoItem>) {
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }
}