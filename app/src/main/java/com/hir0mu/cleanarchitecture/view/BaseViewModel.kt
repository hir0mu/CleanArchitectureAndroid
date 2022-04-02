package com.hir0mu.cleanarchitecture.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hir0mu.cleanarchitecture.R
import com.hir0mu.cleanarchitecture.domain.businessmodel.BusinessModel
import com.hir0mu.cleanarchitecture.domain.usecase.Usecase
import com.hir0mu.cleanarchitecture.domain.usecase.UsecaseInput
import com.hir0mu.cleanarchitecture.domain.usecase.EmptyUsecaseInput
import com.hir0mu.cleanarchitecture.data.NetworkNotAvailableException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel(
    protected val viewModelArgs: ViewModelArgs
) : ViewModel() {

    private val _error: MutableLiveData<Failure> = MutableLiveData()
    val error: LiveData<Failure> = _error

    private val _loading: MutableLiveData<Loading> = MutableLiveData()
    val loading: LiveData<Loading> = _loading

    protected fun <MODEL : BusinessModel> Usecase<EmptyUsecaseInput, MODEL>.execute(
        execution: Execution = DefaultExecution,
        onSuccess: (MODEL) -> Unit = {},
        retry: () -> Unit
    ) {
        execute(
            execution = execution,
            args = EmptyUsecaseInput,
            onSuccess = onSuccess,
            retry = retry
        )
    }

    protected fun <ARGS : UsecaseInput, MODEL : BusinessModel> Usecase<ARGS, MODEL>.execute(
        execution: Execution = DefaultExecution,
        args: ARGS,
        onSuccess: (MODEL) -> Unit = {},
        retry: () -> Unit
    ) {
        viewModelScope.launch {
            execute(args)
                .flowOn(viewModelArgs.dispatcherIO)
                .onStart { _loading.postValue(Loading(execution, true)) }
                .onCompletion { _loading.postValue(Loading(execution, false)) }
                .catch {
                    when (it) {
                        is HttpException -> {
                            val failure = Failure(execution, it, it.toMessage(), retry)
                            _error.postValue(failure)
                        }
                        is NetworkNotAvailableException -> {
                            val failure =
                                Failure(execution, it, R.string.error_network_not_available, retry)
                            _error.postValue(failure)
                        }
                        else -> {
                            val failure = Failure(execution, it, R.string.error_other, retry)
                            _error.postValue(failure)
                        }
                    }
                }
                .collect { onSuccess(it) }
        }
    }

    private fun HttpException.toMessage(): Int {
        return when (code()) {
            400 -> R.string.error_400
            401 -> R.string.error_401
            403 -> R.string.error_403
            404 -> R.string.error_404
            500 -> R.string.error_500
            else -> R.string.error_default
        }
    }
}

class ViewModelArgs(
    val dispatcherIO: CoroutineDispatcher
)
