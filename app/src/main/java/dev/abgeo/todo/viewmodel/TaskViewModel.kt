package dev.abgeo.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import dev.abgeo.todo.entity.Task
import dev.abgeo.todo.repository.TaskRepository

class TaskViewModel : ViewModel() {

    private val _tasksLiveData = MutableLiveData<List<Task>>()
    val tasksLiveData: LiveData<List<Task>>
        get() = _tasksLiveData

    private val _taskLiveData = MutableLiveData<Task>()
    val taskLiveData: LiveData<Task>
        get() = _taskLiveData

    fun postTask(task: Task?) {
        _taskLiveData.postValue(task)
    }

    fun postTasks(tasks: List<Task>?) {
        _tasksLiveData.postValue(tasks)
    }

    fun getTasks(context: Context) {
        postTasks(TaskRepository.getTasks(context))
    }

}
