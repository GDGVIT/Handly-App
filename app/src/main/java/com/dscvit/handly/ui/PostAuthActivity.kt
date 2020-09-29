package com.dscvit.handly.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dscvit.handly.R
import com.dscvit.handly.util.Constants
import com.dscvit.handly.util.PrefHelper
import com.dscvit.handly.util.PrefHelper.set
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_post_auth.*

class PostAuthActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_auth)

        setSupportActionBar(toolbar)

        val sharedPref = PrefHelper.customPrefs(this, Constants.PREF_NAME)
        Log.d(TAG, sharedPref.getString(Constants.PREF_AUTH_TOKEN, "")!!)
        Log.d(TAG, OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sign_out) {
            val sharedPref = PrefHelper.customPrefs(this, Constants.PREF_NAME)
            sharedPref[Constants.PREF_AUTH_TOKEN] = ""
            sharedPref[Constants.PREF_IS_AUTH] = false

            val intent = Intent(this, PreAuthActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
