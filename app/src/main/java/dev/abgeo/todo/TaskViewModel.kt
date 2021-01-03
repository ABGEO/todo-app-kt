package dev.abgeo.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _taskLiveData = MutableLiveData<Task>()
    val taskLiveData: LiveData<Task>
        get() = _taskLiveData

    fun postTask(task: Task) {
        _taskLiveData.postValue(task)
    }

}
