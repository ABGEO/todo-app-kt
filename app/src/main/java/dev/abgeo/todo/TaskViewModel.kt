package dev.abgeo.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context

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

    fun getTasks(context: Context) {
        _tasksLiveData.postValue(TaskRepository.getTasks(context))
    }

}
