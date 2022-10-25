package com.androidjunior9.beatprocrastination.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskentity WHERE id=:id")
    suspend fun getTask(id:Int):TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task:TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM taskentity WHERE isDone=0")
    fun getAllTasks():Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskentity WHERE isDone=1")
    fun getCompletedTasks():Flow<List<TaskEntity>>


}