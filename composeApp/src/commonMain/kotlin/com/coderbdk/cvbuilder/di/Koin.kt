package com.coderbdk.cvbuilder.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(viewModelModule, repositoryModule)
    }
}