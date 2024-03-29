package com.hir0mu.cleanarchitecture.domain.usecase

import kotlinx.coroutines.flow.Flow

interface Usecase<INPUT : UsecaseInput, OUTPUT> {
    fun execute(input: INPUT): Flow<OUTPUT>
}
