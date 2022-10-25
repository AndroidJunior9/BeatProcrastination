package com.androidjunior9.beatprocrastination.data.repository

import com.androidjunior9.beatprocrastination.data.local.TaskDataBase
import com.androidjunior9.beatprocrastination.data.mappers.toTask
import com.androidjunior9.beatprocrastination.data.mappers.toTaskEntity
import com.androidjunior9.beatprocrastination.domain.model.Task
import com.androidjunior9.beatprocrastination.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    db:TaskDataBase
):TaskRepository{

    private val dao = db.dao
    override suspend fun getTask(id: Int): Task? {
        return dao.getTask(id)?.toTask()
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toTaskEntity())
    }


    override suspend fun getAllTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map{
            it.map{ task->
               task.toTask()
            }
        }
    }

    override suspend fun getCompletedTasks(): Flow<List<Task>> {
        return dao.getCompletedTasks().map {
            it.map{
                it.toTask()
            }
        }
    }
}