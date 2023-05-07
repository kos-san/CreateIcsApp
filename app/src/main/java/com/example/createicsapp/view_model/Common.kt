package com.example.createicsapp.view_model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Common {

    /**
     * String型の時刻から、LocalDateTime型に変換。
     * 入力されたdateTimeが、不正または空文字の場合、現在時刻を返す。
     *
     * @param dateTime String型の時刻（yyyymmddHHmm）を設定。
     *
     * @return LocalDateTime 変換したLocalDateTime型
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringConvertToLocalDateTime(dateTime: String) : LocalDateTime{

        var resultLocalDateTime : LocalDateTime = LocalDateTime.now()

        if(dateTime.isNullOrBlank()) {
            return resultLocalDateTime
        }


        try {
            resultLocalDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"))
        } catch (e: Exception) {
            Log.w("日付のキャストに失敗", e.message.toString())
            return resultLocalDateTime
        }

        return resultLocalDateTime


    }

}