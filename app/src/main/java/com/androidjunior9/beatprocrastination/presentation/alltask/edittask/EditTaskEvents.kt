package com.androidjunior9.beatprocrastination.presentation.alltask.edittask

sealed class EditTaskEvents {
    data class OnTitleChange(val title:String):EditTaskEvents()
    data class OnDescriptionChange(val description:String):EditTaskEvents()
    object OnSave:EditTaskEvents()
    data class  OnReminderChange(val timeStamp:Long?):EditTaskEvents()
}