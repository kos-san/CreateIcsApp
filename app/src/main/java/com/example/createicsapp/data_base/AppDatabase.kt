package com.example.createicsapp.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.createicsapp.data.Calendar
import com.example.createicsapp.data.dao.CalendarDao

@Database(entities = [Calendar::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
}