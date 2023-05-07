package com.example.createicsapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.createicsapp.view.calendar_detail.CalendarDetailScreen
import com.example.createicsapp.view.calendar_list.CalendarListScreen
import com.example.createicsapp.view.ui.theme.CreateIcsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.File
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CreateIcsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.CalendarListScreen.root,
                    ) {
                        
                        // カレンダーリスト画面
                        composable(route = ScreenRoute.CalendarListScreen.root) {
                            CalendarListScreen(navController)
                        }
                        
                        // カレンダー詳細画面
                        composable(route = ScreenRoute.CalendarDetailScreen.root + "/{editDisplayDiv}/{calendarId}") {
                            CalendarDetailScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
