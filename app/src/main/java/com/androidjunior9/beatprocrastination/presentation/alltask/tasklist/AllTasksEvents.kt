package com.androidjunior9.beatprocrastination.presentation.alltask.tasklist

import com.androidjunior9.beatprocrastination.domain.model.Task

sealed class AllTasksEvents{
    data class OnDeleteTask(val task: Task): AllTasksEvents()
    data class OnInsertTask(val task:Task): AllTasksEvents()
    data class OnChecked(val task:Task): AllTasksEvents()
}
