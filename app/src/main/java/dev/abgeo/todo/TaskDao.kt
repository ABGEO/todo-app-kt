package dev.abgeo.todo

import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert
    fun insertTask(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun updateTask(task: Task)

}
