package com.androidjunior9.beatprocrastination.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.androidjunior9.beatprocrastination.presentation.alltask.edittask.EditTaskScreen
import com.androidjunior9.beatprocrastination.presentation.alltask.tasklist.AllTasksScreen
import com.androidjunior9.beatprocrastination.presentation.completedtask.CompletedTaskScreen

@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues
){
    NavHost(navController = navController, startDestination = Routes.TASK_LIST_SCREEN ){
        composable(route = Routes.TASK_LIST_SCREEN){
            AllTasksScreen(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
        composable(
            route = Routes.EDIT_TASK_SCREEN + "?taskId={taskId}",
            arguments = listOf(
                navArgument(
                    name = "taskId"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            EditTaskScreen(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
        composable(
            route = Routes.COMPLETED_TASK_SCREEN
        ){
            CompletedTaskScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}