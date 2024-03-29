package com.packapps.model.audio_core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.packapps.model.utils.LogApp
import io.reactivex.subjects.PublishSubject

class MediaBroadcastNotificationActions(val publishSubject: PublishSubject<String>) : BroadcastReceiver() {



    companion object {
        //Action from Broadcast in buttons notification
        const val NOTIFICATION_ACTION_PLAY = "com.packapps.mrremember.ACTION_PLAY"
        const val NOTIFICATION_ACTION_PAUSE = "com.packapps.mrremember.ACTION_PAUSE"

    }


    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        if (action == NOTIFICATION_ACTION_PAUSE){
            LogApp.i("NOTIFICATION", "Action pause")
            publishSubject.onNext(NOTIFICATION_ACTION_PAUSE)

        }else if (action == NOTIFICATION_ACTION_PLAY ){
            LogApp.i("NOTIFICATION", "Action play")
            publishSubject.onNext(NOTIFICATION_ACTION_PLAY)

        }
    }



}