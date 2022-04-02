package com.hiring.cleanarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
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
            .shouldReceive("a", 0)
            .shouldReceive("b", 1)
            .shouldReceive("c", 2)
            .withValueCount(3)
            .end()
    }

    @Test
    fun test_await_failed() {
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
                .shouldReceive("a", 0)
                .shouldReceive("b", 1)
                .shouldReceive("c", 2)
                .withValueCount(3)
                .end()
            fail()
        } catch (e: Exception) {
            assertTrue(e is TimeoutException)
        }
    }
}
