package com.dscvit.handly.di.modules

import com.dscvit.handly.network.ApiClient
import com.dscvit.handly.network.ApiService
import org.koin.dsl.module

val apiModule = module {
    factory { ApiService.createRetrofit(get()) }
    factory { ApiClient(get()) }
}