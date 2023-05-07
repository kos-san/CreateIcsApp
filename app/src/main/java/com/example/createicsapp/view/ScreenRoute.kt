package com.example.createicsapp.view

sealed class ScreenRoute(val root: String) {
    object CalendarListScreen : ScreenRoute("calendar_list_screen")
    object CalendarDetailScreen : ScreenRoute("calendar_detail_screen")
}
