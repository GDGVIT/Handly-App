package com.dscvit.handly.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dscvit.handly.R

class PreAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_auth)

        supportActionBar!!.hide()
    }
}
