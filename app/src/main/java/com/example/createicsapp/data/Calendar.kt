package com.example.createicsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Calendar(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var title: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var place: String = "",
    var description: String = "",
) {
}