package com.example.createicsapp.view_model

import com.example.createicsapp.data.dao.CalendarDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class CalendarListViewModel @Inject constructor(
    private val calendarDao: CalendarDao
) : CalendarViewModelCommon(calendarDao){

    val calendars = calendarDao.loadAllCalendar()
        .distinctUntilChanged() // 変更があっても内容が変わっていない場合は更新しない
}