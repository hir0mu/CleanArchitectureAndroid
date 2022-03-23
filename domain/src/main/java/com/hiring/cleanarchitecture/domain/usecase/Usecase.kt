package com.hiring.cleanarchitecture.domain.usecase

import com.hiring.cleanarchitecture.domain.businessmodel.BusinessModel
import kotlinx.coroutines.flow.Flow

interface Usecase<ARGS : UsecaseArgs, RESULT : BusinessModel> {
    fun execute(args: ARGS): Flow<RESULT>
}

interface UsecaseArgs

object UsecaseArgsUnit : UsecaseArgs
