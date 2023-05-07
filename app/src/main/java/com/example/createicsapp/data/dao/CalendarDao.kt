package com.example.createicsapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.createicsapp.data.Calendar
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarDao {
    @Insert
    suspend fun insertCalendar(calendar: Calendar)

    @Query("SELECT * FROM Calendar")
    fun loadAllCalendar(): Flow<List<Calendar>>

    @Update
    suspend fun updateCalendar(calendar: Calendar)

    @Delete
    suspend fun deleteCalendar(calendar: Calendar)

    @Query("SELECT * FROM Calendar WHERE id = :id")
    suspend fun loadCalendarById(id: Int) : Calendar

    @Query("SELECT MAX(id) FROM Calendar")
    suspend fun getMaxId() : Int

}