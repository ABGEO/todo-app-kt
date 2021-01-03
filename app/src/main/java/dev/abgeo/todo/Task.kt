package dev.abgeo.todo

data class Task(
    val title: String,
    val body: String,
    val isCompleted: Boolean = false,
)
