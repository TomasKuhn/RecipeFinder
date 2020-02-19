package com.tkuhn.recipefinder.di

import com.tkuhn.recipefinder.cache.ForecastCacheData
import org.koin.dsl.module


val cacheModule = module {
    single { ForecastCacheData(get(), get()) }
}