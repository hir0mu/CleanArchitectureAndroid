package com.hiring.cleanarchitecture.view.list

data class SearchParams(
    val itemId: String,
    val page: Int
) {
    companion object {
        val EMPTY = SearchParams("", 0)
    }
}
