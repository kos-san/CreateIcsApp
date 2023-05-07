package com.example.createicsapp.view.calendar_list

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.createicsapp.data.Calendar
import com.example.createicsapp.enums.EditDisplayDiv
import com.example.createicsapp.view.ScreenRoute
import com.example.createicsapp.view.components.CalendarList
import com.example.createicsapp.view.components.DeleteConfirmationDialog
import com.example.createicsapp.view_model.CalendarListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalendarListScreen(
    navController: NavController,
    viewModel: CalendarListViewModel = hiltViewModel(),
) {

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(ScreenRoute.CalendarDetailScreen.root + "/${EditDisplayDiv.CREATE_NEW.name}/0")
        }
        ) {
           Icon(imageVector = Icons.Default.Add, contentDescription = "新規作成")
        }
    }) {
        val calendars by viewModel.calendars.collectAsState(initial = emptyList())

        var calendar: MutableState<Calendar?> = remember {
            mutableStateOf(null)
        }

        if(viewModel.isShowDeleteConfirmationDialog) {
            DeleteConfirmationDialog(
                viewModel = viewModel,
                calendar = calendar.value!!,
                navController = navController,
            )
        }

        CalendarList(
            calendars = calendars,
            onClickRow = {
                navController.navigate(ScreenRoute.CalendarDetailScreen.root + "/${EditDisplayDiv.EDIT.name}/${it.id}")
            },
            onClickDelete = {
                viewModel.isShowDeleteConfirmationDialog = true
                calendar.value = it
            }
        )

    }

}