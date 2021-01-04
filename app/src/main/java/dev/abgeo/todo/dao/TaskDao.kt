package dev.abgeo.todo.dao

import androidx.room.*
import dev.abgeo.todo.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert
    fun insertTask(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM task WHERE is_completed = 1")
    fun deleteCompleted()

    @Update
    fun updateTask(task: Task)

}
