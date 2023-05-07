package com.example.createicsapp.view_model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.createicsapp.data.Calendar
import com.example.createicsapp.data.dao.CalendarDao
import com.example.createicsapp.enums.EditDisplayDiv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarDetailViewModel @Inject constructor(
    private val calendarDao: CalendarDao,
    savedStateHandle: SavedStateHandle
) : CalendarViewModelCommon(calendarDao){

    var id: Int? by mutableStateOf(null)

    var title by mutableStateOf("")
    var startTime by mutableStateOf("")
    var endTime by mutableStateOf("")
    var place by mutableStateOf("")
    var description by mutableStateOf("")

    val dtE = DateTimeFormatter.ofPattern("E")
    var startTimeYear by mutableStateOf(Common().stringConvertToLocalDateTime(startTime).year.toString().padStart(4,'0'))
    var startTimeMonth by mutableStateOf(Common().stringConvertToLocalDateTime(startTime).month.value.toString().padStart(2,'0'))
    var startTimeDay by mutableStateOf(Common().stringConvertToLocalDateTime(startTime).dayOfMonth.toString().padStart(2,'0'))
    var startTimeHour by mutableStateOf(Common().stringConvertToLocalDateTime(startTime).hour.toString().padStart(2,'0'))
    var startTimeMinutes by mutableStateOf(Common().stringConvertToLocalDateTime(startTime).minute.toString().padStart(2,'0'))

    var startDayOfWeek by mutableStateOf(
        dtE.format(
            LocalDate.of(
                startTimeYear.toInt(),
                startTimeMonth.toInt(),
                startTimeDay.toInt()
            )
        )
    )

    var endTimeYear by mutableStateOf(Common().stringConvertToLocalDateTime(endTime).year.toString().padStart(4,'0'))
    var endTimeMonth by mutableStateOf(Common().stringConvertToLocalDateTime(endTime).month.value.toString().padStart(2,'0'))
    var endTimeDay by mutableStateOf(Common().stringConvertToLocalDateTime(endTime).dayOfMonth.toString().padStart(2,'0'))
    var endTimeHour by mutableStateOf(Common().stringConvertToLocalDateTime(endTime).hour.toString().padStart(2,'0'))
    var endTimeMinutes by mutableStateOf(Common().stringConvertToLocalDateTime(endTime).minute.toString().padStart(2,'0'))

    var endDayOfWeek by mutableStateOf(
        dtE.format(
            LocalDate.of(
                endTimeYear.toInt(),
                endTimeMonth.toInt(),
                endTimeDay.toInt()
            )
        )
    )

    var editCalendar: Calendar? by mutableStateOf(null)

    val editDisplayDiv: EditDisplayDiv
        get() = if(null == id) EditDisplayDiv.CREATE_NEW else EditDisplayDiv.EDIT

    init {
        savedStateHandle
            .get<String>("editDisplayDiv")?.let { div ->
                if (EditDisplayDiv.EDIT.name == div) {

                    savedStateHandle.get<String>("calendarId")?.let { calendarId ->
                        if (!calendarId.isNullOrBlank()) id = calendarId.toInt()
                    }

                }

            }

        loadCalendar()
    }

    private fun loadCalendar() {

        if (EditDisplayDiv.EDIT == editDisplayDiv && null == editCalendar) {
            viewModelScope.launch {
                editCalendar = calendarDao.loadCalendarById(id!!)
                title = editCalendar!!.title
                startTime = editCalendar!!.startTime
                endTime = editCalendar!!.endTime
                place = editCalendar!!.place
                description = editCalendar!!.description

                setDateParts(editCalendar!!)
            }
        }

    }




    fun createCalendar() {
        viewModelScope.launch {
            val newCalendar = Calendar(
                title = title,
                startTime = startTime,
                endTime = endTime,
                place = place,
                description = description
            )

            calendarDao.insertCalendar(newCalendar)
            Log.d("作成成功", "successCreate!!")

            id = calendarDao.getMaxId()
            Log.d("ID", id.toString())

            editCalendar = calendarDao.loadCalendarById(id!!)
        }
    }

    fun updateCalendar() {
        editCalendar?.let { calendar ->
            viewModelScope.launch {
                calendar.title = title
                calendar.startTime = startTime
                calendar.endTime = endTime
                calendar.place = place
                calendar.description = description

                calendarDao.updateCalendar(calendar)
            }

        }
    }


    private fun setDateParts(calendar: Calendar) {
        startTimeYear = Common().stringConvertToLocalDateTime(calendar.startTime).year.toString().padStart(4,'0')
        startTimeMonth = Common().stringConvertToLocalDateTime(calendar.startTime).month.value.toString().padStart(2,'0')
        startTimeDay = Common().stringConvertToLocalDateTime(calendar.startTime).dayOfMonth.toString().padStart(2,'0')
        startTimeHour = Common().stringConvertToLocalDateTime(calendar.startTime).hour.toString().padStart(2,'0')
        startTimeMinutes = Common().stringConvertToLocalDateTime(calendar.startTime).minute.toString().padStart(2,'0')

        startDayOfWeek = dtE.format(
            LocalDate.of(
                startTimeYear.toInt(),
                startTimeMonth.toInt(),
                startTimeDay.toInt()
            )
        )


        endTimeYear = Common().stringConvertToLocalDateTime(calendar.endTime).year.toString().padStart(4,'0')
        endTimeMonth = Common().stringConvertToLocalDateTime(calendar.endTime).month.value.toString().padStart(2,'0')
        endTimeDay = Common().stringConvertToLocalDateTime(calendar.endTime).dayOfMonth.toString().padStart(2,'0')
        endTimeHour = Common().stringConvertToLocalDateTime(calendar.endTime).hour.toString().padStart(2,'0')
        endTimeMinutes = Common().stringConvertToLocalDateTime(calendar.endTime).minute.toString().padStart(2,'0')

        endDayOfWeek = dtE.format(
            LocalDate.of(
                endTimeYear.toInt(),
                endTimeMonth.toInt(),
                endTimeDay.toInt()
            )
        )
    }

}