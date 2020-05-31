package com.dscvit.handly.ui.auth

import android.content.Intent
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
import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.ui.PostAuthActivity
import com.dscvit.handly.util.*
import com.dscvit.handly.util.PrefHelper.set
import com.github.ybq.android.spinkit.style.Wave
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.fragment_signin.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class SigninFragment : Fragment() {

    private val TAG = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authViewModel by sharedViewModel<AuthViewModel>()
        val sharedPref = PrefHelper.customPrefs(requireContext(), Constants.PREF_NAME)

        val action = SigninFragmentDirections.actionSigninFragmentToSignupFragment()
        signin_progress.hide()
        signin_progress.setIndeterminateDrawable(Wave())

        signup_instead_text.setOnClickListener {
            findNavController().navigate(action)
        }

        signin_button.setOnClickListener {
            val email = signin_email_text.editText?.text.toString()
            val password = signin_password_text.editText?.text.toString()

            if (!email.isValidEmail()) {
                signin_email_text.error = getString(R.string.email_error_text)
            }

            if (password.length < 6) {
                signin_password_text.error = getString(R.string.password_error_text)
            }

            signin_email_text.editText?.doOnTextChanged {text, _, _, _ ->
                if (text.isValidEmail()) {
                    signin_email_text.error = null
                } else {
                    signin_email_text.error = getString(R.string.email_error_text)
                }
            }

            signin_password_text.editText?.doOnTextChanged {text, _, _, _ ->
                if (text!!.length > 5) {
                    signin_password_text.error = null
                } else {
                    signin_password_text.error = getString(R.string.password_error_text)
                }
            }

            if (email.isValidEmail() && password.length > 5) {
                val signinRequest = SigninRequest(
                    password,
                    OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId ?: "",
                    email
                )

                authViewModel.signinUser(signinRequest).observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is Result.Loading -> {
                            hideUi()
                            signin_progress.show()
                        }
                        is Result.Success -> {
                            if (it.data!!.message == "User Logged In") {
                                sharedPref[Constants.PREF_IS_AUTH] = true
                                sharedPref[Constants.PREF_AUTH_TOKEN] = it.data.payload.token

                                val intent = Intent(requireContext(), PostAuthActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                                shortToast("Signin Successful")
                            }
                            signin_progress.hide()
                        }
                        is Result.Error -> {
                            Log.d(TAG, it.message!!)
                            if (it.message == "400 Bad Request") {
                                shortToast("Invalid credentials")
                            } else {
                                shortToast("Oops, Something wrong on our end")
                            }
                            signin_progress.hide()
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
        loginText.hide()
        signin_email_text.hide()
        signin_password_text.hide()
        signup_instead_text.hide()
        signin_button.hide()
    }

    private fun showUi() {
        titleText.show()
        logo.show()
        loginText.show()
        signin_email_text.show()
        signin_password_text.show()
        signup_instead_text.show()
        signin_button.show()
    }
}
