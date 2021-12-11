package com.hiring.data

class MemoryCache<T>(
    private val expiryAge: Long = DEFAULT_AGE,
    private val maxSize: Int = MAX_SIZE
) {
    companion object {
        private const val DEFAULT_AGE = 1000 * 60 * 20L // 20 minutes
        private const val MAX_SIZE = 100
    }

    private val caches: HashMap<String, Entry<T>> = hashMapOf()

    fun get(key: String): T? {
        val cache = caches[key] ?: return null

        if (cache.isExpired()) {
            caches.remove(key)
            return null
        }
        return cache.value
    }

    fun put(key: String, value: T) {
        caches.keys.forEach {
            if (caches[it]?.isExpired() == true) {
                caches.remove(it)
            }
        }
        caches[key] = Entry(value, expiryAge)

        if (caches.size > maxSize) {
            caches.keys.toList()
                .takeLast(caches.size - maxSize)
                .forEach { caches.remove(it) }
        }
    }

    private class Entry<T>(
        val value: T,
        private val expiryAge: Long
    ) {
        private val timestamp: Long = System.currentTimeMillis()

        fun isExpired(): Boolean {
            return System.currentTimeMillis() - timestamp > expiryAge
        }
    }
}
