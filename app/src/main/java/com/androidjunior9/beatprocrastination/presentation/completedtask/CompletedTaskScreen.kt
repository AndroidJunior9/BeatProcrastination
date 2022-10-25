package com.androidjunior9.beatprocrastination.presentation.completedtask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androidjunior9.beatprocrastination.ui.theme.DarkGray

@Composable
fun CompletedTaskScreen(
    modifier:Modifier = Modifier,
    viewModel: CompletedTaskViewModel = hiltViewModel(),
){
    val state = viewModel.state
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                DarkGray
            ),
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
            items(state.completedTasks) { task ->
                CompletedTaskItem(
                    onDone = viewModel::onUndone,
                    task = task,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}