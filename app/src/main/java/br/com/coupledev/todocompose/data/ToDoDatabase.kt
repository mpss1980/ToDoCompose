package br.com.coupledev.todocompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.coupledev.todocompose.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}