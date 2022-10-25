package com.androidjunior9.beatprocrastination.di

import android.app.Application
import androidx.room.Room
import com.androidjunior9.beatprocrastination.data.local.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(app:Application):TaskDataBase{
        return Room.databaseBuilder(
            app,
            TaskDataBase::class.java,
            "taskdb.db"
        ).build()
    }
}