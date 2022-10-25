package com.androidjunior9.beatprocrastination.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title:String,
    val isDone: Boolean,
    val description:String?,
    val reminder:Long?,
    @PrimaryKey val id:Int? = null
)
