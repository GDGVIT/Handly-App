package com.dscvit.handly.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dscvit.handly.R
import com.dscvit.handly.ui.PreAuthActivity
import com.dscvit.handly.util.Constants
import com.dscvit.handly.util.PrefHelper
import com.dscvit.handly.util.PrefHelper.set
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = PrefHelper.customPrefs(requireContext(), Constants.PREF_NAME)

        signout_button.setOnClickListener {
            sharedPref[Constants.PREF_AUTH_TOKEN] = ""
            sharedPref[Constants.PREF_IS_AUTH] = false

            val intent = Intent(requireContext(), PreAuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}
