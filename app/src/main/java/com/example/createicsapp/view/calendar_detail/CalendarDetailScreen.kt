package com.example.createicsapp.view.calendar_detail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.createicsapp.enums.DisplayDiv
import com.example.createicsapp.enums.EditDisplayDiv
import com.example.createicsapp.ics_file_controller.IcsFileController
import com.example.createicsapp.view.ScreenRoute
import com.example.createicsapp.view.components.DeleteConfirmationDialog
import com.example.createicsapp.view_model.CalendarDetailViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDetailScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    viewModel: CalendarDetailViewModel = hiltViewModel()
) {
    
    if(viewModel.isShowDeleteConfirmationDialog) {
        DeleteConfirmationDialog(
            viewModel = viewModel,
            calendar = viewModel.editCalendar!!,
            navController = navController,
        )
    }
    
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {

        // タイトルフィールド.
        Text(
            text = "タイトル",
            fontWeight = FontWeight.Bold
        )

        TextField(
            singleLine = true,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = viewModel.title,
            onValueChange = {
                viewModel.title = it
            }
        )

        Spacer(modifier = Modifier.height(5.dp))
        
        // 開始日 と 時刻.
        Text(text = "開始時刻")
        DateAndTimePicker(displayDiv = DisplayDiv.START)

        viewModel.startTime = viewModel.startTimeYear + viewModel.startTimeMonth + viewModel.startTimeDay +
                viewModel.startTimeHour + viewModel.startTimeMinutes

        Spacer(modifier = Modifier.height(10.dp))

        // 終了日 と 時刻.
        Text(text = "終了時刻")
        DateAndTimePicker(displayDiv = DisplayDiv.END)

        viewModel.endTime = viewModel.endTimeYear + viewModel.endTimeMonth + viewModel.endTimeDay +
                viewModel.endTimeHour + viewModel.endTimeMinutes

        Spacer(modifier = Modifier.height(10.dp))

        // 場所.
        Text(text = "場所")
        TextField(
            singleLine = true,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = viewModel.place,
            onValueChange = {
                viewModel.place = it
            }
        )

        // 詳細.
        Text(text = "詳細")
        TextField(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(300.dp),
            value = viewModel.description,
            onValueChange = {
                viewModel.description = it
            },


        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            IconButton(onClick = {
                navController.navigate(ScreenRoute.CalendarListScreen.root)
            }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "戻る")
                    Text(text = "一覧へ戻る")
                }
            }

            Spacer(modifier = Modifier.width(5.dp))

            IconButton(
                onClick = {
                    if (EditDisplayDiv.CREATE_NEW == viewModel.editDisplayDiv) {
                        viewModel.createCalendar()
                        val toast = Toast.makeText(context, "スケジュールを作成しました。", Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        viewModel.updateCalendar()
                        val toast = Toast.makeText(context, "スケジュールを更新しました。", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (EditDisplayDiv.CREATE_NEW == viewModel.editDisplayDiv) Icons.Default.Add else Icons.Default.Create,
                        contentDescription =  "保存",
                    )
                    Text(text = if (EditDisplayDiv.CREATE_NEW == viewModel.editDisplayDiv) "保存する" else "更新する")
                }
            }

            Spacer(modifier = Modifier.width(5.dp))

            IconButton(
                onClick = {

                    if (EditDisplayDiv.CREATE_NEW == viewModel.editDisplayDiv) {
                        viewModel.createCalendar()
                    } else {
                        viewModel.updateCalendar()
                    }

                    val icsFileController = IcsFileController(context = context, calendarData = viewModel.editCalendar)
                    icsFileController.createOutFile()
                    icsFileController.sendFile()
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "共有")
                    Text(text = "共有する")
                }

            }

            Spacer(modifier = Modifier.width(5.dp))

            IconButton(
                onClick = {
                    viewModel.isShowDeleteConfirmationDialog = true
                },
                enabled = EditDisplayDiv.EDIT == viewModel.editDisplayDiv
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
                    Text(text = "削除する")
                }
            }

        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateAndTimePicker(
    viewModel: CalendarDetailViewModel = hiltViewModel(),
    displayDiv: DisplayDiv
) {

    val dtE = DateTimeFormatter.ofPattern("E")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // 開始日.
        val context = LocalContext.current


        TextButton(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(5.dp),
                    color = Color.Black
                )
                .padding(horizontal = 5.dp)
                .height(height = 35.dp),
            onClick = {
                showDatePicker(
                    context = context,
                    year = if (DisplayDiv.START == displayDiv) viewModel.startTimeYear else viewModel.endTimeYear,
                    month = if (DisplayDiv.START == displayDiv) viewModel.startTimeMonth else viewModel.endTimeMonth,
                    day = if (DisplayDiv.START == displayDiv) viewModel.startTimeDay else viewModel.endTimeDay,
                    onDecideDate = { year, month, day ->

                        if (DisplayDiv.START == displayDiv) {
                            viewModel.startTimeYear = year.padStart(4,'0')
                            viewModel.startTimeMonth = month.padStart(2,'0')
                            viewModel.startTimeDay = day.padStart(2,'0')
                            viewModel.startDayOfWeek = dtE.format( LocalDate.of(
                                year.toInt(),
                                month.toInt(),
                                day.toInt()
                            ))

                        } else {
                            viewModel.endTimeYear = year.padStart(4,'0')
                            viewModel.endTimeMonth = month.padStart(2,'0')
                            viewModel.endTimeDay = day.padStart(2,'0')
                            viewModel.endDayOfWeek = dtE.format( LocalDate.of(
                                year.toInt(),
                                month.toInt(),
                                day.toInt()
                            ))
                        }
                    }
                )
            }
        ) {
            if(DisplayDiv.START == displayDiv)
                Text(text = "${viewModel.startTimeYear}年${viewModel.startTimeMonth}月${viewModel.startTimeDay}日（${viewModel.startDayOfWeek}）")
            else
                Text(text = "${viewModel.endTimeYear}年${viewModel.endTimeMonth}月${viewModel.endTimeDay}日（${viewModel.endDayOfWeek}）")

        }

        Spacer(modifier = Modifier.width(20.dp))

        TextButton(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(5.dp),
                    color = Color.Black
                )
                .padding(horizontal = 5.dp)
                .height(35.dp),
            onClick = {
                showTimePicker(
                    context = context,
                    hour = if(DisplayDiv.START == displayDiv) viewModel.startTimeHour else viewModel.endTimeHour,
                    minutes = if(DisplayDiv.START == displayDiv) viewModel.startTimeMinutes else viewModel.endTimeMinutes,
                    onDecideTime = { hour, minutes ->
                        if(DisplayDiv.START == displayDiv) {
                            viewModel.startTimeHour = hour.padStart(2,'0')
                            viewModel.startTimeMinutes = minutes.padStart(2,'0')
                        } else {
                            viewModel.endTimeHour = hour.padStart(2,'0')
                            viewModel.endTimeMinutes = minutes.padStart(2,'0')
                        }
                    }
                )
            }
        ) {
            if(DisplayDiv.START == displayDiv)
                Text(text = "${viewModel.startTimeHour}：${viewModel.startTimeMinutes}")
            else
                Text(text = "${viewModel.endTimeHour}：${viewModel.endTimeMinutes}")

        }
    }
}

fun showDatePicker(
    context: Context,
    onDecideDate: (String, String, String) -> Unit,
    year: String,
    month: String,
    day: String
) {
    val calendar = Calendar.getInstance()
    calendar.time = Date()

    DatePickerDialog(
        context,
        { _: DatePicker, pickedYear: Int, pickedMonth: Int, pickedDay: Int ->
            onDecideDate(pickedYear.toString(), (pickedMonth + 1).toString(), pickedDay.toString())
        }, year.toInt(), month.toInt() - 1, day.toInt()
    ).show()
}

fun showTimePicker(
    context: Context,
    onDecideTime: (String, String) -> Unit,
    hour: String,
    minutes: String
) {

    val calendar = Calendar.getInstance()

    calendar.time = Date()

    TimePickerDialog(
        context,
        { _: TimePicker, pickedH: Int, pickedM: Int ->
            onDecideTime(pickedH.toString(), pickedM.toString())
        }, hour.toInt(), minutes.toInt(),
        true
    ).show()

}