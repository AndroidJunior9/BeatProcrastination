package com.androidjunior9.beatprocrastination.di

import com.androidjunior9.beatprocrastination.data.repository.TaskRepositoryImpl
import com.androidjunior9.beatprocrastination.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ):TaskRepository
}