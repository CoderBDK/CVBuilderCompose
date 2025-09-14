package com.coderbdk.cvbuilder.di

import com.coderbdk.cvbuilder.data.repository.EditorRepository
import com.coderbdk.cvbuilder.data.repository.EditorRepositoryImpl
import com.coderbdk.cvbuilder.data.repository.TemplateRepository
import com.coderbdk.cvbuilder.data.repository.TemplateRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::TemplateRepositoryImpl) bind TemplateRepository::class
    singleOf(::EditorRepositoryImpl) bind EditorRepository::class
}