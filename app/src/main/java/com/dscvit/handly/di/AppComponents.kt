package com.dscvit.handly.di

import com.dscvit.handly.di.modules.apiModule
import com.dscvit.handly.di.modules.repoModule

val appComponents = listOf(apiModule, repoModule)