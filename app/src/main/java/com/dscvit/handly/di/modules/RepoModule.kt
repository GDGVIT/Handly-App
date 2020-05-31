package com.dscvit.handly.di.modules

import com.dscvit.handly.repository.AppRepo
import org.koin.dsl.module

val repoModule = module {
    factory { AppRepo(get()) }
}