package com.hiring.cleanarchitecture.domain.usecase

import kotlinx.coroutines.flow.Flow

interface Usecase<ARGS: UsecaseArgs, RESULT> {
    fun execute(args: ARGS): Flow<RESULT>
}

interface UsecaseArgs

object UsecaseArgsUnit: UsecaseArgs
