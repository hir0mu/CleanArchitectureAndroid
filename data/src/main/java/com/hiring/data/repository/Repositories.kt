package com.hiring.data.repository

import com.hiring.data.MemoryCache

internal suspend fun <T> call(key: String, memoryCache: MemoryCache<T>, api: suspend () -> T): T {
    return when (val cache = memoryCache.get(key)) {
        null -> api().also { memoryCache.put(key, it) }
        else -> cache
    }
}
