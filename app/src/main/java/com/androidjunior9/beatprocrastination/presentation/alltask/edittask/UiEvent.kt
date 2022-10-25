package com.androidjunior9.beatprocrastination.presentation.alltask.edittask

sealed class UiEvent{
    object SaveNote:UiEvent()
    data class ShowSnackBar(val message:String):UiEvent()
}
