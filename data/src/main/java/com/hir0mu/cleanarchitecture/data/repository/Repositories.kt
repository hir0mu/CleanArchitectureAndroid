package com.hir0mu.cleanarchitecture.data.repository

import com.hir0mu.cleanarchitecture.data.MemoryCache
import com.hir0mu.cleanarchitecture.data.NetworkManager
import com.hir0mu.cleanarchitecture.data.NetworkNotAvailableException

internal suspend fun <T> call(manager: NetworkManager, api: suspend () -> T): T {
    manager.requireNotOffline()
    return api()
}

internal suspend fun <T> callWithCache(
    manager: NetworkManager,
    key: String,
    memoryCache: MemoryCache<T>,
    api: suspend () -> T
): T {
    return when (val cache = memoryCache.get(key)) {
        null -> call(manager, api).also { memoryCache.put(key, it) }
        else -> cache
    }
}

private fun NetworkManager.requireNotOffline() {
    if (!isConnected) {
        throw NetworkNotAvailableException()
    }
}
