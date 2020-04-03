package com.tkuhn.recipefinder.di

import com.tkuhn.recipefinder.datasource.database.Db
import com.tkuhn.recipefinder.datasource.network.RetrofitService
import com.tkuhn.recipefinder.repository.RecipesRepo
import org.koin.core.scope.Scope
import org.koin.dsl.module

val repoModule = module {
    single { RecipesRepo(createApiService(), get()) }
    single { RetrofitService() }
    single { Db.getInstance() }
}

private inline fun <reified T> Scope.createApiService(): T = get<RetrofitService>().createApiService()