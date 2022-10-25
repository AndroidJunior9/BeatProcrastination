package com.androidjunior9.beatprocrastination.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
abstract class TaskDataBase:RoomDatabase(){
    abstract val dao:TaskDao
}