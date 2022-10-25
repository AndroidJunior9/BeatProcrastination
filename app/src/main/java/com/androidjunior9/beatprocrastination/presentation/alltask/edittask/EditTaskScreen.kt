package com.androidjunior9.beatprocrastination.presentation.alltask.edittask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.androidjunior9.beatprocrastination.ui.theme.DarkGray
import com.androidjunior9.beatprocrastination.ui.theme.Teal200
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditTaskScreen(
    modifier:Modifier = Modifier,
    navController: NavController,
    viewModel: EditTaskViewModel = hiltViewModel()
){
    val titleState = viewModel.title
    val descriptionState = viewModel.description
    val task = viewModel.task
    val reminder = viewModel.getLongValue()
    val isDialogOpen = remember{
        mutableStateOf(false)
    }
    if(isDialogOpen.value){
    AddReminderDialog(isDialogOpen = {
                                     isDialogOpen.value = it
    }, onEvent = viewModel::onEvent)}

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
     LaunchedEffect(key1 = true){
         viewModel.eventFlow.collectLatest { event->
             when(event){
                 is UiEvent.SaveNote -> {
                     navController.navigateUp()
                 }

                 is UiEvent.ShowSnackBar -> {
                     scaffoldState.snackbarHostState.showSnackbar(
                         message = event.message
                     )
                 }
             }
         }
     }
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(EditTaskEvents.OnSave)
            },
                backgroundColor = Teal200
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save Note")
            }
        },
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(DarkGray)
                .padding(16.dp)
        ){
            Text(
                text = "Add/Edit Task",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White

            )
            TextField(value = titleState, onValueChange = {
                viewModel.onEvent(EditTaskEvents.OnTitleChange(it))
            },
                textStyle = TextStyle(fontSize = 20.sp),
                placeholder = {
                    Text(
                        text = "Title",
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    textColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(value = descriptionState, onValueChange = {
                viewModel.onEvent(EditTaskEvents.OnDescriptionChange(it))
            },
                textStyle = TextStyle(fontSize = 14.sp),
                placeholder = {
                    Text(
                        text = "Description",
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    textColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            viewModel.task?.id?.let {
                OutlinedButton(
                    onClick = {
                        isDialogOpen.value = true
                    },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = DarkGray
                )
                ){
                    if(reminder.toString().isEmpty() || reminder==null){
                        Text(
                            text = "Add a Reminder"
                        )
                    }else {
                        Row() {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${reminder.dayOfYear} " +
                                            "${reminder.monthValue} " +
                                            "${reminder.year} ",
                                    style = TextStyle(fontSize = 14.sp, color = Color.LightGray),
                                    modifier = Modifier.padding(2.dp)
                                )
                                Text(
                                    text = "${reminder.hour} ${reminder.minute}",
                                    style = TextStyle(fontSize = 14.sp, color = Color.LightGray),
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                            IconButton(onClick = {
                                task?.let{
                                    viewModel.onEvent(EditTaskEvents.OnReminderChange(null))
                                    viewModel.cancelAlarmFromOutside(it)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "cancel reminder"
                                )
                            }
                        }

                    }
                }

            }

        }

    }
}
