package com.androidjunior9.beatprocrastination.presentation.completedtask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidjunior9.beatprocrastination.domain.model.Task
import com.androidjunior9.beatprocrastination.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedTaskViewModel @Inject constructor(
    private val repository: TaskRepository
):ViewModel() {
    var state by mutableStateOf(CompletetdTaskState())
    init{
        getCompletetdTasks()
    }
    private fun getCompletetdTasks(){
        viewModelScope.launch{
            repository.getCompletedTasks().collect{
                state = state.copy(
                    completedTasks = it
                )
            }
        }
    }

    fun onUndone(task: Task){
        viewModelScope.launch{
            repository.insertTask(task.copy(isDone = false))
        }
    }


}