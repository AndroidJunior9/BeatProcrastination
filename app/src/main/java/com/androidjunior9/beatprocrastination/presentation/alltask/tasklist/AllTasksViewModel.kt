package com.androidjunior9.beatprocrastination.presentation.alltask.tasklist

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidjunior9.beatprocrastination.domain.model.Task
import com.androidjunior9.beatprocrastination.domain.repository.TaskRepository
import com.androidjunior9.beatprocrastination.presentation.alltask.edittask.AlertReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTasksViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val application: Application,
):ViewModel() {
    var state by mutableStateOf(AllTasksState())
        private set
    init{
        getTasks()
    }
    fun onEvent(event: AllTasksEvents){
        when(event){
            is AllTasksEvents.OnDeleteTask -> {
                viewModelScope.launch {
                    if (event.task.reminder!=null){
                        cancelAlarm(application.applicationContext,event.task)
                    }
                    repository.deleteTask(event.task)
                }
            }
            is AllTasksEvents.OnInsertTask -> {
                viewModelScope.launch{
                    repository.insertTask(event.task)
                }
            }
            is AllTasksEvents.OnChecked -> {
                viewModelScope.launch {
                    repository.insertTask(event.task.copy(
                        isDone = true
                    ))
                    delay(1100)
                }
            }

        }
    }
    private fun getTasks(){
        viewModelScope.launch {
            repository.getAllTasks().collect { tasks ->
                state = state.copy(
                    tasks = tasks
                )
            }
        }
    }
    fun cancelAlarm(context: Context, task: Task) {
        task.id?.let {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlertReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    it,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
                )
            alarmManager.cancel(pendingIntent)
        }
    }
}


