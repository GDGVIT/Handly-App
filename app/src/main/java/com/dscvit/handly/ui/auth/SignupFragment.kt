package com.dscvit.handly.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dscvit.handly.R
import com.dscvit.handly.model.Result
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.util.*
import com.dscvit.handly.util.PrefHelper.set
import com.github.ybq.android.spinkit.style.Wave
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.fragment_signup.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


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

        val authViewModel by sharedViewModel<AuthViewModel>()
        val sharedPref = PrefHelper.customPrefs(requireContext(), Constants.PREF_NAME)

        val action = SignupFragmentDirections.actionSignupFragmentToSigninFragment()
        signup_progress.setIndeterminateDrawable(Wave())
        signup_progress.hide()

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

            signup_email_text.editText!!.doOnTextChanged { text, _, _, _ ->
                if (text.isValidEmail()) {
                    signup_email_text.error = null
                } else {
                    signup_email_text.error = "Please enter a valid email"
                }
            }

            signup_password_text.editText!!.doOnTextChanged { text, _, _, _ ->
                if (text!!.trim().length > 5) {
                    signup_password_text.error = null
                } else {
                    signup_password_text.error = "Password must have at least 6 characters"
                }
            }

            signup_name_text.editText!!.doOnTextChanged { text, _, _, _ ->
                if (text!!.trim().length >= 2) {
                    signup_name_text.error = null
                } else {
                    signup_name_text.error = "Password must have at least 6 characters"
                }
            }

            if (email.isValidEmail() && password.trim().length > 5 && name.trim().length >= 2) {
                val signupRequest = SignupRequest(
                    name,
                    password,
                    OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId ?: "",
                    email
                )

                authViewModel.signupUser(signupRequest).observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        Result.Status.LOADING -> {
                            hideUi()
                            signup_progress.show()
                        }
                        Result.Status.SUCCESS -> {
                            if (it.data!!.message == "User Logged In") {
                                sharedPref[Constants.PREF_IS_AUTH] = true
                                shortToast("Signup Successful")
                            }
                            signup_progress.hide()
                        }
                        Result.Status.ERROR -> {
                            Log.d("esh", it.message!!)
                            if (it.message == "400 Bad Request") {
                                shortToast("User Exists, Try logging in")
                            } else {
                                shortToast("Oops, Something wrong on our end")
                            }
                            signup_progress.hide()
                            showUi()
                        }
                    }
                })
            }
        }
    }

    private fun hideUi() {
        titleText.hide()
        logo.hide()
        registerText.hide()
        signup_name_text.hide()
        signup_email_text.hide()
        signup_password_text.hide()
        signup_button.hide()
        signin_instead_text.hide()
    }

    private fun showUi() {
        titleText.show()
        logo.show()
        registerText.show()
        signup_name_text.show()
        signup_email_text.show()
        signup_password_text.show()
        signup_button.show()
        signin_instead_text.show()
    }
}
