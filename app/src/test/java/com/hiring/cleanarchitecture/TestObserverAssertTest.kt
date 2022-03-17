package com.hiring.cleanarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.util.concurrent.Executors
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class TestObserverAssertTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_await() {
        runTest {

        }
        // Given
        val liveData = MutableLiveData<String>()
        val testObserver = liveData.testObserver()

        // When
        liveData.postValue("a")
        liveData.postValue("b")
        Executors.newSingleThreadExecutor().submit {
            Thread.sleep(500)
            liveData.postValue("c")
        }

        // Then
        testObserver.await(3)
            .shouldReceive("a")
            .shouldReceiveAt("a", 0)
            .shouldReceiveAt("b", 1)
            .shouldReceiveAt("c", 2)
            .withValueCount(3)
            .end()
    }

fun <T> Flow<T>.await(): T {
    return runBlocking { first() }
}

    fun <T> Flow<T>.await(count: Int): T {
        return runBlocking { first() }
    }

    @Test
    fun test_await_failed() {
        flow<String> {  }.first()
        // Given
        val liveData = MutableLiveData<String>()
        val testObserver = liveData.testObserver()

        // When
        liveData.postValue("a")
        liveData.postValue("b")
        Executors.newSingleThreadExecutor().submit {
            Thread.sleep(1500)
            liveData.postValue("c")
        }

        // Then
        try {
            testObserver.await(3)
                .shouldReceive("a")
                .shouldReceiveAt("a", 0)
                .shouldReceiveAt("b", 1)
                .shouldReceiveAt("c", 2)
                .withValueCount(3)
                .end()
            fail()
        } catch (e: Exception) {
            assertTrue(e is TimeoutException)
        }
    }
}
