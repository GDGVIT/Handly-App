package com.dscvit.handly.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.dscvit.handly.R
import com.dscvit.handly.util.Constants
import com.dscvit.handly.util.PrefHelper
import com.google.android.material.tabs.TabLayout
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_post_auth.*

class PostAuthActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_auth)

        val sharedPref = PrefHelper.customPrefs(this, Constants.PREF_NAME)
        Log.d(TAG, sharedPref.getString(Constants.PREF_AUTH_TOKEN, "")!!)
        Log.d(TAG, OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId)
    }
}
