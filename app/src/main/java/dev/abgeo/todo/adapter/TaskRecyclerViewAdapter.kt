package dev.abgeo.todo.adapter

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import dev.abgeo.todo.R
import dev.abgeo.todo.entity.Task

class TaskRecyclerViewAdapter(
        private val tasks: List<Task>,
        private val taskCheckedListener: TaskCheckedListener,
        private val taskClickListener: TaskClickListener
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_task_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTaskTitle.text = task.title

        if (task.isCompleted) {
            holder.cbTaskTitle.isChecked = true
            holder.tvTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.cbTaskTitle.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                holder.tvTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.tvTaskTitle.paintFlags = Paint.ANTI_ALIAS_FLAG
            }

            taskCheckedListener.onTaskCheckedListener(task, isChecked)
        }

        holder.tvTaskTitle.setOnClickListener {
            taskClickListener.onTaskClickListener(task)
        }
    }

    override fun getItemCount(): Int = tasks.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbTaskTitle: CheckBox = view.findViewById(R.id.cbTaskTitle)
        val tvTaskTitle: TextView = view.findViewById(R.id.tvTaskTitle)
    }

    interface TaskCheckedListener {
        fun onTaskCheckedListener(task: Task, isChecked: Boolean)
    }

    interface TaskClickListener {
        fun onTaskClickListener(task: Task)
    }

}