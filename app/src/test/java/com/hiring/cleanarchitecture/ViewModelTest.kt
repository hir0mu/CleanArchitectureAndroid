package com.hiring.cleanarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations
import java.lang.Exception

@ExperimentalCoroutinesApi
abstract class ViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    fun <T> flowOfException(): Flow<T> {
        return flow { throw Exception() }
    }

    abstract fun setup()

    @Before
    fun setupBefore() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        setup()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
