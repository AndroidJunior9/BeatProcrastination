package com.androidjunior9.beatprocrastination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.androidjunior9.beatprocrastination.ui.theme.BeatProcrastinationTheme
import com.androidjunior9.beatprocrastination.util.BottomNavItem
import com.androidjunior9.beatprocrastination.util.BottomNavScreen
import com.androidjunior9.beatprocrastination.util.Navigation
import com.androidjunior9.beatprocrastination.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeatProcrastinationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavScreen(
                                navController = navController,
                                items = listOf(
                                    BottomNavItem(
                                        name = "Tasks",
                                        route = Routes.TASK_LIST_SCREEN,
                                        icon = Icons.Default.List
                                    ),
                                    BottomNavItem(
                                        name = "Comleted Tasks",
                                        route = Routes.COMPLETED_TASK_SCREEN,
                                        icon = Icons.Default.Done
                                    )
                                ),
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    ) {
                        Navigation(
                            navController = navController,
                            paddingValues = it
                        )
                    }
                }
            }
        }
    }
}

