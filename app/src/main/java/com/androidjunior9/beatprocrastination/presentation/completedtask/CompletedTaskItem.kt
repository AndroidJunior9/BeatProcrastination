package com.androidjunior9.beatprocrastination.presentation.completedtask

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
fun CompletedTaskItem(
    modifier: Modifier = Modifier,
    onDone:(Task) -> Unit,
    task: Task
) {
    var isChecked by remember {
        mutableStateOf(true)
    }
    Card(
        modifier = modifier,
        backgroundColor = DarkGray,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp,color = Color.White)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(4f),
            ) {

                Text(
                    text = task.title,
                    style = TextStyle(fontSize = 20.sp, color = Color.White),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                task.description?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontSize = 14.sp, color = Color.White),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5
                    )
                }
            }

            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = true
                    onDone(task)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}