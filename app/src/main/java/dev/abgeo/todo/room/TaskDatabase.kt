package dev.abgeo.todo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.abgeo.todo.dao.TaskDao
import dev.abgeo.todo.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabaseClient(context: Context): TaskDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, TaskDatabase::class.java, "todo-app-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                return INSTANCE!!
            }
        }
    }

}