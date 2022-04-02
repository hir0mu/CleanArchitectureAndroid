package com.hir0mu.cleanarchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Assert
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.testObserver(): TestObserverAssert<T> {
    return TestObserverAssert(this)
}

class TestObserverAssert<T>(private val target: LiveData<T>) {

    private var latch: CountDownLatch? = null
    private val values = mutableListOf<T>()
    private val observer = Observer<T> {
        values.add(it)
        latch?.countDown()
    }

    init {
        target.observeForever(observer)
    }

    fun await(
        count: Int = 1,
        timeout: Long = 1,
        unit: TimeUnit = TimeUnit.SECONDS
    ): TestObserverAssert<T> {
        val valueCount = values.size
        if (valueCount >= count) {
            return this
        }

        val latch = CountDownLatch(count - valueCount)
        this.latch = latch
        if (!latch.await(timeout, unit)) {
            throw TimeoutException()
        }
        return this
    }

    fun shouldReceive(expected: T, at: Int = 0): TestObserverAssert<T> {
        Assert.assertEquals(expected, values[at])
        return this
    }

    fun withValueCount(expected: Int): TestObserverAssert<T> {
        Assert.assertEquals(expected, values.size)
        return this
    }

    fun end() {
        target.removeObserver(observer)
    }
}
