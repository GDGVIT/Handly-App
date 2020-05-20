package com.dscvit.handly.di.modules

import com.dscvit.handly.ui.auth.AuthViewModel
import com.dscvit.handly.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}