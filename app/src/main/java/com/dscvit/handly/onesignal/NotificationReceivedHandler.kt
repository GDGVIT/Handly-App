package com.dscvit.handly.onesignal

import android.util.Log
import com.onesignal.NotificationExtenderService
import com.onesignal.OSNotificationReceivedResult


class NotificationReceivedHandler: NotificationExtenderService() {
    override fun onNotificationProcessing(notification: OSNotificationReceivedResult?): Boolean {
        val data = notification!!.payload.additionalData
        Log.d("esh", data.getString("url"))

        return false
    }
}