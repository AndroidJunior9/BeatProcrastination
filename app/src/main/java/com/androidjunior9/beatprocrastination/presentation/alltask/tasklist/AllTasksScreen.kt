package com.androidjunior9.beatprocrastination.presentation.alltask.tasklist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.androidjunior9.beatprocrastination.ui.theme.DarkGray
import com.androidjunior9.beatprocrastination.ui.theme.Teal200
import com.androidjunior9.beatprocrastination.util.Routes

@Composable
fun AllTasksScreen(
    modifier:Modifier = Modifier,
    viewModel: AllTasksViewModel = hiltViewModel(),
    navController: NavController
){
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.EDIT_TASK_SCREEN)
            },
            backgroundColor = Teal200) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add A New Task"
                )
            }
        }
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    DarkGray
                ).padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Tasks",
                style = TextStyle(fontSize = 20.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.Start)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.tasks) { task ->
                    TaskItem(
                        task = task,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    Routes.EDIT_TASK_SCREEN + "?taskId=${task.id}"
                                )
                            }
                    )
                }
            }
        }
    }
}