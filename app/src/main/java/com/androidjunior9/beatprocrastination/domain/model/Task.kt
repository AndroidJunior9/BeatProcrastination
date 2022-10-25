package com.androidjunior9.beatprocrastination.domain.model

data class Task(
    val title:String,
    val isDone: Boolean,
    val description:String?,
    val id:Int? = null,
    val reminder:Long? = null
)
