package com.dscvit.handly.firebase

import android.util.Log
import com.dscvit.handly.util.Constants
import com.dscvit.handly.util.PrefHelper
import com.dscvit.handly.util.PrefHelper.set
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("esh", remoteMessage.notification!!.title!!)
        Log.d("esh", remoteMessage.notification!!.body!!)
    }

    override fun onNewToken(token: String) {
        Log.d("esh", token)
        val sharedPreferences = PrefHelper.customPrefs(this, Constants.PREF_NAME)
        sharedPreferences[Constants.PREF_FCM_TOKEN] = token

        super.onNewToken(token)
    }
}