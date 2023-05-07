package com.example.createicsapp.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.createicsapp.data.Calendar
import com.example.createicsapp.data.dao.CalendarDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * カレンダーリスト表示画面、カレンダー詳細表示画面の画面モデルで共通的に使用する機能・変数を定義する。
 */
@HiltViewModel
open class CalendarViewModelCommon @Inject constructor(
    private val calendarDao: CalendarDao
) : ViewModel(){

    /** スケジュールの削除確認ダイアログ表示フラグ */
    var isShowDeleteConfirmationDialog by mutableStateOf(false)

    fun deleteCalendar(calendar: Calendar) {
        viewModelScope.launch {
            calendarDao.deleteCalendar(calendar)
        }
    }
}