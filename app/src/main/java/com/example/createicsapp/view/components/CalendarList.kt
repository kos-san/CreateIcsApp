package com.example.createicsapp.view.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.createicsapp.data.Calendar

@Composable
fun CalendarList(
    calendars: List<Calendar>,
    onClickRow: (Calendar) -> Unit,
    onClickDelete: (Calendar) -> Unit
) {
    LazyColumn {
        items(calendars) { calendar ->
            CalendarFileRow(
                calendar = calendar,
                onClickRow = onClickRow,
                onClickDelete = onClickDelete
            )
        }
    }
}