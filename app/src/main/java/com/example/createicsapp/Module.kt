package com.example.createicsapp

import android.content.Context
import androidx.room.Room
import com.example.createicsapp.data_base.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "calendar_database").build()

    @Provides
    fun provideDao(db: AppDatabase) = db.calendarDao()
}