package com.dscvit.handly.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dscvit.handly.R

class PreAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_auth)

        supportActionBar!!.hide()
    }
}
