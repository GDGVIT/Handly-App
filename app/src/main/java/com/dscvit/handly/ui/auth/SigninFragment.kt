package com.dscvit.handly.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dscvit.handly.R
import kotlinx.android.synthetic.main.fragment_signin.*


class SigninFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = SigninFragmentDirections.actionSigninFragmentToSignupFragment()
        signup_instead_text.setOnClickListener {
            findNavController().navigate(action)
        }

        signin_button.setOnClickListener {

        }
    }
}
