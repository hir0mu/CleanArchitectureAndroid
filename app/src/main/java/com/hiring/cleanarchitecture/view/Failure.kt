package com.hiring.cleanarchitecture.view

import androidx.annotation.StringRes

data class Failure(
    val execution: Execution,
    val error: Throwable,
    @StringRes val message: Int,
    val retry: () -> Unit
)
