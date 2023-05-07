package com.example.createicsapp.view.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.createicsapp.data.Calendar
import com.example.createicsapp.view.ScreenRoute
import com.example.createicsapp.view_model.CalendarViewModelCommon

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeleteConfirmationDialog(
    viewModel: CalendarViewModelCommon,
    calendar: Calendar,
    navController: NavController
) {

    AlertDialog(
        onDismissRequest = { viewModel.isShowDeleteConfirmationDialog = false },
        title = {
            Text(text = "確認")
        },
        text = {
            Column {
                Text(text = "スケジュールを削除します。よろしいですか？")
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = {
                        viewModel.isShowDeleteConfirmationDialog = false
                    }) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    ),
                    onClick = {
                        viewModel.isShowDeleteConfirmationDialog = false
                        viewModel.deleteCalendar(calendar)
                        navController.navigate(ScreenRoute.CalendarListScreen.root)
                    }) {
                    Text(
                        color = Color.White,
                        text = "Delete"
                    )
                }
            }

        }

    )

}