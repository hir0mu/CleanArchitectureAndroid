package com.hir0mu.cleanarchitecture.view.list

data class SearchParams(
    val itemId: String,
    val page: Int
) {
    companion object {
        val EMPTY = SearchParams("", 0)
    }

    fun isEmpty(): Boolean {
        return this == EMPTY
    }
}
