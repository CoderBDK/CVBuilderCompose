package com.coderbdk.cvbuilder.di

import com.coderbdk.cvbuilder.ui.editor.EditorViewModel
import com.coderbdk.cvbuilder.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::EditorViewModel)
}