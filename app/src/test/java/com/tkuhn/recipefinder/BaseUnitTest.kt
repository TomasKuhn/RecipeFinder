package com.tkuhn.recipefinder

import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

@ExtendWith(InstantExecutorExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseUnitTest : KoinTest {

    abstract val testingModules: Module

    @ObsoleteCoroutinesApi
    private val mainThreadDispatcher = newSingleThreadContext("UI thread")

    @BeforeAll
    fun initKoinModules() {
        MockKAnnotations.init(this)
        startKoin {
            modules(testingModules)
        }
    }

    @AfterAll
    fun clearKoin() {
        stopKoin()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @BeforeAll
    fun setupMainThreadDispatcher() {
        Dispatchers.setMain(mainThreadDispatcher)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @AfterAll
    fun resetDispatchers() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadDispatcher.close()
    }
}