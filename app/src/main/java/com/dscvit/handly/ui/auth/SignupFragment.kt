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
import kotlinx.android.synthetic.main.fragment_signup.*


class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = SignupFragmentDirections.actionSignupFragmentToSigninFragment()

        signin_instead_text.setOnClickListener {
            findNavController().navigate(action)
        }

        signup_button.setOnClickListener {
            val email = signup_email_text.editText!!.text.toString()
            val password = signup_password_text.editText!!.text.toString()
            val name = signup_name_text.editText!!.text.toString()

            if (!email.isValidEmail()) {
                signup_email_text.error = "Please enter a valid email"
            }

            if (password.trim().length < 6) {
                signup_password_text.error = "Password must have at least 6 characters"
            }

            if (name.trim().length < 2) {
                signup_name_text.error = "Please enter a valid name"
            }

            signup_email_text.editText!!.doOnTextChanged {text, _, _, _ ->
                if (text.isValidEmail()) {
                    signup_email_text.error = null
                } else {
                    signup_email_text.error = "Please enter a valid email"
                }
            }

            signup_password_text.editText!!.doOnTextChanged {text, _, _, _ ->
                if (text!!.trim().length > 5) {
                    signup_password_text.error = null
                } else {
                    signup_password_text.error = "Password must have at least 6 characters"
                }
            }

            signup_name_text.editText!!.doOnTextChanged {text, _, _, _ ->
                if (text!!.trim().length >= 2) {
                    signup_name_text.error = null
                } else {
                    signup_name_text.error = "Password must have at least 6 characters"
                }
            }

            if (email.isValidEmail() && password.trim().length > 5 && name.trim().length >= 2) {
                Log.d("esh", "Valid Sign Up")
            }
        }
    }
}
