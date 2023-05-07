package com.example.createicsapp.ics_file_controller

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.format.DateFormat
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.createicsapp.data.Calendar
import com.example.createicsapp.view_model.Common
import java.io.File
import java.io.IOException
import java.util.Date

class IcsFileController(
    private val context: Context,
    private val calendarData: Calendar? = null,
) {

    private var icsFile: File? = null
    private var fileName: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun createOutFile() {
        val timestamp = DateFormat.format("yyyyMMdd_HHmmss", Date()).toString()
        fileName = "${timestamp}.ics"

        icsFile = File(context.filesDir, fileName)
        icsFile!!.createNewFile()

        writeIcsFile()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun writeIcsFile() {
        if (icsFile == null) throw ControllerException("icsファイルが存在しないまま書き込み操作が実行")
        if (calendarData == null) throw ControllerException("icsファイルに書き込むCalendarデータが存在しないまま書き込み操作が実行")

        try {
            val bufferedWriter = icsFile!!.bufferedWriter()
            val writeResult = bufferedWriter.use {

                it.appendLine("BEGIN:VCALENDAR")
                    it.appendLine("PRODID:-//createicsapp/${fileName}//JAPAN v1.0//EN")
                    it.appendLine("VERSION:2.0")
                    it.appendLine("BEGIN:VTIMEZONE")
                        it.appendLine("TZID:Asia/Tokyo")
                        it.appendLine("BEGIN:STANDARD")
                            it.appendLine("DTSTART:19390101T000000")
                            it.appendLine("TZNAME:JST")
                            it.appendLine("TZOFFSETFROM:+0900")
                            it.appendLine("TZOFFSETTO:+0900")
                        it.appendLine("END:STANDARD")
                    it.appendLine("END:VTIMEZONE")
                    it.appendLine("BEGIN:VEVENT")
                        // startTime.
                        it.appendLine("DTSTART;TZID=Asia/Tokyo:${convertDTTime(calendarData.startTime)}")
                        // endTime.
                        it.appendLine("DTEND;TZID=Asia/Tokyo:${convertDTTime(calendarData.endTime)}")
                        // title.
                        it.appendLine("SUMMARY:${calendarData.title}")
                        // description.
                        it.appendLine("DESCRIPTION:${calendarData.description.replace(Regex("""\n"""), """\\n""")}")
                        // location.
                        it.appendLine("LOCATION:${calendarData.place}")
                        it.appendLine("TRANSP:OPAQUE")
                    it.appendLine("END:VEVENT")
                it.appendLine("END:VCALENDAR")
            }

            print(calendarData.description.replace(Regex("""\n"""), """\\n"""))

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun sendFile() {

        val file = File(context.filesDir, fileName)

        val uri = FileProvider.getUriForFile(context, "com.example.createicsapp.fileprovider", file)

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/calendar"
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        context.startActivity(Intent.createChooser(intent, "Share ICS file"))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertDTTime(time: String): String {
        val lDT = Common().stringConvertToLocalDateTime(time)

        return lDT.year.toString().padStart(4, '0') +
                lDT.month.value.toString().padStart(2, '0') +
                lDT.dayOfMonth.toString().padStart(2, '0') +
                "T" +   // --ここから時間--.
                lDT.hour.toString().padStart(2, '0') +
                lDT.minute.toString().padStart(2, '0') +
                "00"
    }

}
