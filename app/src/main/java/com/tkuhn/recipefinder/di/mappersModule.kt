package com.tkuhn.recipefinder.di

import com.tkuhn.recipefinder.repository.mapper.RecipeMapper
import org.koin.dsl.module

val mappersModule = module {
    single { RecipeMapper() }
}