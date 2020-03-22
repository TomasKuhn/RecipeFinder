package com.tkuhn.recipefinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.clearStaticMockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest


abstract class BaseUnitTest : KoinTest {

    abstract val testingModules: Module

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadDispatcher = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun before() {
        startKoin {
            modules(testingModules)
        }

        Dispatchers.setMain(mainThreadDispatcher)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun after() {
        clearStaticMockk()

        stopKoin()

        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadDispatcher.close()
    }

    protected fun mockResources() {
        mockkStatic("com.tkuhn.recipefinder.utils.extensions.ResourcesExtensionsKt")
    }
}