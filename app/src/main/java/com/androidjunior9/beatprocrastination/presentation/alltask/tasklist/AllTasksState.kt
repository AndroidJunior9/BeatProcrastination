package com.androidjunior9.beatprocrastination.presentation.alltask.tasklist

import com.androidjunior9.beatprocrastination.domain.model.Task

data class AllTasksState(
    val tasks:List<Task> = emptyList(),
)
