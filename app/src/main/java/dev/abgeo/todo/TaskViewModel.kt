package dev.abgeo.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.ArrayList

class TaskViewModel : ViewModel() {

    private val _tasksLiveData = MutableLiveData<List<Task>>()
    val tasksLiveData: LiveData<List<Task>>
        get() = _tasksLiveData

    private val _taskLiveData = MutableLiveData<Task>()
    val taskLiveData: LiveData<Task>
        get() = _taskLiveData

    fun postTask(task: Task) {
        _taskLiveData.postValue(task)
    }

    fun getTasks() {
        // TODO
        val tasks: MutableList<Task> = ArrayList()
        for (i in 1..10) {
            tasks.add((Task("Task $i", "Task $i", i % 2 == 0)))
        }

        _tasksLiveData.postValue(tasks)
    }

}
