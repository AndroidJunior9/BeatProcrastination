package com.androidjunior9.beatprocrastination.presentation.alltask.edittask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.androidjunior9.beatprocrastination.R
import com.androidjunior9.beatprocrastination.domain.model.Task

const val STRING_EXTRA = "title"
class AlertReceiver :BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        try{
            val bundle = p1.extras
            val title = bundle?.getString("title")
            showNotification(p0,title?:"")
        }catch(e:Exception){
            Log.d("Error",e.printStackTrace().toString())
        }
    }
    private fun showNotification(context: Context,title:String){
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "task_channel"
        val channelId = "task_id"

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH,
        )
        manager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context,channelId)
            .setContentTitle("Task Reminder")
            .setContentText(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        val randomnum = (1..1000000000000000000).random()
        manager.notify(randomnum.toInt(),builder.build())

    }
}