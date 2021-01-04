package dev.abgeo.todo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "body") var body: String,
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
