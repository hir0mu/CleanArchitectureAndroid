package com.hiring.cleanarchitecture.view

import java.lang.Exception

data class Failure(
    val error: Throwable,
    val message: Int,
    val retry: () -> Unit
)
