package com.androidjunior9.beatprocrastination.data.mappers

import com.androidjunior9.beatprocrastination.data.local.TaskEntity
import com.androidjunior9.beatprocrastination.domain.model.Task

fun TaskEntity.toTask():Task{
    return Task(
        title = title,
        isDone = isDone,
        description = description,
        id = id,
        reminder = reminder
    )
}

fun Task.toTaskEntity():TaskEntity{
    return TaskEntity(
        title = title,
        isDone = isDone,
        description = description,
        id = id,
        reminder = reminder
    )
}