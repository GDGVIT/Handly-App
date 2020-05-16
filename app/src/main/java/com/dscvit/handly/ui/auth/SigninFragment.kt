package com.dscvit.handly.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dscvit.handly.R
import com.dscvit.handly.util.isValidEmail
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
            val email = signin_email_text.editText!!.text.toString()
            val password = signin_password_text.editText!!.text.toString()

            if (!email.isValidEmail()) {
                signin_email_text.error = "Please enter a valid email"
            }

            if (password.length < 6) {
                signin_password_text.error = "Password must have at least 6 characters"
            }

            signin_email_text.editText!!.doOnTextChanged {text, _, _, _ ->
                if (text.isValidEmail()) {
                    signin_email_text.error = null
                } else {
                    signin_email_text.error = "Please enter a valid email"
                }
            }

            signin_password_text.editText!!.doOnTextChanged {text, _, _, _ ->
                if (text!!.length > 5) {
                    signin_password_text.error = null
                } else {
                    signin_password_text.error = "Password must have at least 6 characters"
                }
            }

            if (email.isValidEmail() && password.length > 5) {
                Log.d("esh", "Valid Login")
            }
        }
    }
}
