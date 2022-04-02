package com.hiring.cleanarchitecture.domain.usecase

import com.hiring.cleanarchitecture.domain.businessmodel.BusinessModel
import kotlinx.coroutines.flow.Flow

interface Usecase<ARGS : UsecaseArgs, BUSINESS_MODEL : BusinessModel> {
    fun execute(args: ARGS): Flow<BUSINESS_MODEL>
}
