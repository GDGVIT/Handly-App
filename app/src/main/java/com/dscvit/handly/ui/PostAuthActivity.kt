package com.dscvit.handly.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.dscvit.handly.R
import com.dscvit.handly.util.Constants
import com.dscvit.handly.util.PrefHelper
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_post_auth.*

class PostAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_auth)

        setupNavigation()

        val sharedPref = PrefHelper.customPrefs(this, Constants.PREF_NAME)
        Log.d("esh", sharedPref.getString(Constants.PREF_AUTH_TOKEN, "")!!)
        Log.d("esh", OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId)
    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(
            bottom_nav,
            post_auth_nav_host.findNavController()
        )
    }
}
