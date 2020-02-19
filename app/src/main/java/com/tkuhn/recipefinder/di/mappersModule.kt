package com.tkuhn.recipefinder.di

import com.tkuhn.recipefinder.repository.mapper.ForecastMappers
import org.koin.dsl.module


val mappersModule = module {
    single { ForecastMappers() }
}