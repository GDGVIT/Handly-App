package com.dscvit.handly.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dscvit.handly.R
import com.dscvit.handly.util.Constants
import com.dscvit.handly.util.PrefHelper

class PreAuthActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val sharedPref = PrefHelper.customPrefs(this, Constants.PREF_NAME)
        val isAuth = sharedPref.getBoolean(Constants.PREF_IS_AUTH, false)
        if (isAuth) {
            val intent = Intent(this, PostAuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_auth)
    }
}
