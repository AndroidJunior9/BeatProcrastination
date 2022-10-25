package com.androidjunior9.beatprocrastination.presentation.alltask.edittask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AddReminderDialog(
    isDialogOpen:(Boolean) -> Unit,
    onEvent:(EditTaskEvents) -> Unit
){
    Dialog(onDismissRequest = {isDialogOpen(false)}) {
        Box(
            modifier = Modifier
            .fillMaxWidth()
                .background(color = Color.White,shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Add Reminder",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    IconButton(
                        onClick = { isDialogOpen(false) },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Exit")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val mContext = LocalContext.current

                    // Declaring and initializing a calendar
                    val mCalendar = Calendar.getInstance()
                    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
                    val mMinute = mCalendar[Calendar.MINUTE]
                    val mTime = remember {
                        mutableStateOf("")
                    }

                    val mTimePickerDialog = TimePickerDialog(
                        mContext,
                        { _, mHour: Int, mMinute: Int ->
                            val hour = if(mHour.toString().length==1) "0$mHour"
                            else mHour.toString()
                            val minutes = if(mMinute.toString().length==1) "0$mMinute"
                            else mMinute.toString()
                            mTime.value = "$hour:$minutes"
                        }, mHour, mMinute, false
                    )
                    Button(
                        onClick = { mTimePickerDialog.show() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        modifier = Modifier.height(50.dp).width(300.dp)
                    ) {
                        Text(text = mTime.value, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    val mMonth: Int
                    val mDay: Int

                    val mYear: Int = mCalendar.get(Calendar.YEAR)
                    mMonth = mCalendar.get(Calendar.MONTH)
                    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

                    mCalendar.time = Date()

                    val mDate = remember { mutableStateOf("") }

                    val mDatePickerDialog = DatePickerDialog(
                        mContext,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            val m = mMonth+1
                            val month = if(m.toString().length==1) "0"+m.toString()
                            else m.toString()
                            val dayOfMonth = if(mDayOfMonth.toString().length==1)
                                "0"+mDayOfMonth.toString() else mDayOfMonth.toString()
                            mDate.value = "$mYear/$month/$dayOfMonth"
                        }, mYear, mMonth, mDay
                    )
                    Button(
                        onClick = { mDatePickerDialog.show() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.LightGray
                        ),
                        modifier = Modifier.width(300.dp).height(50.dp)
                    ) {
                        Text(text = mDate.value, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val date = "${mDate.value} ${mTime.value}"
                            if (date.isNotBlank()) {
                                val tz = OffsetDateTime.now().offset
                                val long = LocalDateTime.parse(
                                    date, DateTimeFormatter
                                        .ofPattern("yyyy/MM/dd HH:mm")
                                ).toEpochSecond(tz)
                                onEvent(EditTaskEvents.OnReminderChange(long))
                                isDialogOpen(false)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.LightGray
                        ),
                        modifier = Modifier.height(50.dp).width(300.dp)
                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))


                }
            }
        }
    }
}