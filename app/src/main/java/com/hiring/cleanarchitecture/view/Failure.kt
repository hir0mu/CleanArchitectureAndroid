package com.hiring.cleanarchitecture.view

data class Failure(
    val execution: Execution,
    val error: Throwable,
    val message: Int,
    val retry: () -> Unit
)
