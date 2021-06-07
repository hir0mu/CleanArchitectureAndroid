package com.hiring.cleanarchitecture.view

import java.lang.Exception

data class Failure(
    val error: Exception,
    val message: Int,
    val retry: () -> Unit
)
