package dev.abgeo.todo

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TaskRepository {

    companion object {
        var database: TaskDatabase? = null

        fun insertTask(context: Context, task: Task) {
            database = TaskDatabase.getDatabaseClient(context)

            CoroutineScope(IO).launch {
                database!!.getTaskDao().insertTask(task)
            }
        }

        fun updateTask(context: Context, task: Task) {
            database = TaskDatabase.getDatabaseClient(context)

            CoroutineScope(IO).launch {
                database!!.getTaskDao().updateTask(task)
            }
        }

        fun deleteCompleted(context: Context) {
            database = TaskDatabase.getDatabaseClient(context)
            database!!.getTaskDao().deleteCompleted()
        }

        fun getTasks(context: Context) : List<Task>? {
            database = TaskDatabase.getDatabaseClient(context)

            return database!!.getTaskDao().getAll()
        }
    }

}
