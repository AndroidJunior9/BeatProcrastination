package com.androidjunior9.beatprocrastination.presentation.completedtask

import com.androidjunior9.beatprocrastination.domain.model.Task

data class CompletetdTaskState(
    val completedTasks:List<Task> = emptyList()
)
