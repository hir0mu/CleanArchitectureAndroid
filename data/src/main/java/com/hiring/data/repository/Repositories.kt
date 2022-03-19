package com.hiring.data.repository

import com.hiring.data.MemoryCache
import com.hiring.data.NetworkManager
import com.hiring.data.NetworkNotAvailableException

internal suspend fun <T> call(manager: NetworkManager, api: suspend  () -> T): T {
    manager.checkNotOffline()
    return api()
}

internal suspend fun <T> callWithCache(manager: NetworkManager, key: String, memoryCache: MemoryCache<T>, api: suspend () -> T): T {
    return when (val cache = memoryCache.get(key)) {
        null -> call(manager, api).also { memoryCache.put(key, it) }
        else -> cache
    }
}

private fun NetworkManager.checkNotOffline() {
    if (!isConnected) {
        throw NetworkNotAvailableException()
    }
}
