package com.dscvit.handly.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dscvit.handly.R
import com.dscvit.handly.util.Constants
import com.dscvit.handly.util.PrefHelper

class PostAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_auth)

        val sharedPref = PrefHelper.customPrefs(this, Constants.PREF_NAME)
        Log.d("esh", sharedPref.getString(Constants.PREF_AUTH_TOKEN, "")!!)
    }
}
