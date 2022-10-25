package com.androidjunior9.beatprocrastination.presentation.alltask.edittask

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidjunior9.beatprocrastination.domain.model.Task
import com.androidjunior9.beatprocrastination.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle,
    private val application: Application,
):ViewModel() {

    var task by mutableStateOf<Task?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var reminder by mutableStateOf<Long?>(null)
        private set
    private var cacheReminder by mutableStateOf<Long?>(null)

    private val _eventFlow = MutableSharedFlow<UiEvent>()

    val eventFlow = _eventFlow.asSharedFlow()

    init{
        val taskId = savedStateHandle.get<Int>("taskId")!!
        if(taskId!=-1){
            viewModelScope.launch {
                repository.getTask(taskId)?.let{
                    title = it.title
                    description = it.description?:""
                    reminder = it.reminder
                    cacheReminder = it.reminder
                    this@EditTaskViewModel.task = it
                }
            }
        }
    }
    fun getLongValue():LocalDateTime?{
        return reminder?.let{
            Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }
    }

    fun setAlarm(context: Context, task: Task){
        val timeSec = task.reminder
        task.id?.let {id->
            timeSec?.let {
                val calendar = Calendar.getInstance()
                val t = getLongValue()
                t?.let { ti ->

                    calendar.set(Calendar.YEAR, ti.year)
                    calendar.set(Calendar.HOUR_OF_DAY, ti.hour)
                    calendar.set(Calendar.MINUTE, ti.minute)
                    calendar.set(Calendar.SECOND, ti.second)
                    calendar.set(Calendar.DAY_OF_YEAR, ti.dayOfYear)
                    calendar.set(Calendar.MONTH, ti.monthValue)
                    val bundle = Bundle()
                    bundle.putString("title",task.title)
                    val intent = Intent(context, AlertReceiver::class.java)
                        .putExtras(bundle)
                    val pendingIntent =
                        PendingIntent.getBroadcast(context, id, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT)
                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                    Log.d("From setAlarm","alarm setted")
                }
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
    fun onEvent(event: EditTaskEvents){
        when(event){
            is EditTaskEvents.OnTitleChange -> {
                title = event.title
            }
            is EditTaskEvents.OnDescriptionChange -> {
                description = event.description
            }
            is EditTaskEvents.OnSave -> {

                viewModelScope.launch{
                    try{
                        val taskModified = Task(
                            title = title,
                            description = description,
                            id = task?.id,
                            isDone = task?.isDone?:false,
                            reminder = reminder
                        )
                        repository.insertTask(
                            taskModified
                        )
                        reminder?.let{remind->
                                if (cacheReminder != null) {
                                    if (remind != cacheReminder) {
                                            cancelAlarm(application.applicationContext, taskModified)
                                            setAlarm(application.applicationContext, taskModified)


                                    }else{
                                        return@let
                                    }
                                } else {
                                        setAlarm(application.applicationContext,taskModified)



                                }
                            }

                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch(e:Exception){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar("Task couldn't be saved")
                        )
                    }

                }
            }
            is EditTaskEvents.OnReminderChange -> {
                reminder = event.timeStamp
            }
        }
    }
    fun cancelAlarmFromOutside(task:Task){
        cancelAlarm(application.applicationContext,task)
    }
}