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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_auth)

        val sharedPref = PrefHelper.customPrefs(this, Constants.PREF_NAME)
        Log.d("esh", sharedPref.getString(Constants.PREF_AUTH_TOKEN, "")!!)
        Log.d("esh", OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId)

        val navController = Navigation.findNavController(this, R.id.post_auth_nav_host)
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()

        tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        navController.navigate(R.id.collectionsFragment, null, navOptions)
                    }
                    1 -> {
                        navController.navigate(R.id.settingsFragment, null, navOptions)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        tab_layout.getTabAt(0)!!.select()
    }
}
