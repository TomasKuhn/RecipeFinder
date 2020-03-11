package com.tkuhn.recipefinder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.mockito.Mockito

@ExtendWith(InstantExecutorExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseUnitTest : KoinTest {

    abstract val testingModules: Module

    private lateinit var lifecycle: LifecycleRegistry

    @BeforeAll
    fun initKoinModules() {
        startKoin {
            modules(testingModules)
        }
        lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))
    }

    @AfterAll
    fun clearKoin() {
        stopKoin()
    }
}