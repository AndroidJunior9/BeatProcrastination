package com.androidjunior9.beatprocrastination.presentation.alltask.tasklist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidjunior9.beatprocrastination.domain.model.Task
import com.androidjunior9.beatprocrastination.ui.theme.DarkGray

@Composable
fun TaskItem(
    task: Task,
    onEvent:(AllTasksEvents)-> Unit,
    modifier:Modifier = Modifier
){
    Card(
        modifier = modifier,
        backgroundColor = DarkGray,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp,Color.White)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(4f)
            ) {

                Text(
                    text = task.title,
                    style = TextStyle(fontSize = 16.sp, color = Color.White),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )

                task.description?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontSize = 12.sp, color = Color.White),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                IconButton(onClick = {
                    onEvent(AllTasksEvents.OnDeleteTask(task))
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete a Task")
                }
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = {
                        onEvent(AllTasksEvents.OnChecked(task))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green
                    )
                )
            }

        }
    }
}