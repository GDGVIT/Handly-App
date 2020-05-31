package com.dscvit.handly.di

import com.dscvit.handly.di.modules.apiModule
import com.dscvit.handly.di.modules.repoModule
import com.dscvit.handly.di.modules.viewModelModule

val appComponents = listOf(apiModule, repoModule, viewModelModule)