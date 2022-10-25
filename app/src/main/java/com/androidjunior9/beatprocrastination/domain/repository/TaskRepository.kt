package com.androidjunior9.beatprocrastination.domain.repository

import com.androidjunior9.beatprocrastination.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTask(id:Int): Task?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun getAllTasks(): Flow<List<Task>>

    suspend fun getCompletedTasks(): Flow<List<Task>>
}