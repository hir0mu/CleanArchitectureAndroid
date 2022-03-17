package com.hiring.cleanarchitecture.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiring.cleanarchitecture.R
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.UsecaseArgs
import com.hiring.cleanarchitecture.domain.usecase.UsecaseArgsUnit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel(
    protected val viewModelArgs: ViewModelArgs
) : ViewModel() {

    private val _error: MutableLiveData<Failure> = MutableLiveData()
    val error: LiveData<Failure> = _error

    protected fun <MODEL> Usecase<UsecaseArgsUnit, MODEL>.execute(
        onSuccess: (MODEL) -> Unit = {},
        retry: () -> Unit
    ) {
        execute(
            args = UsecaseArgsUnit,
            onSuccess = onSuccess,
            retry = retry
        )
    }

    protected fun <ARGS : UsecaseArgs, MODEL> Usecase<ARGS, MODEL>.execute(
        args: ARGS,
        onSuccess: (MODEL) -> Unit = {},
        retry: () -> Unit
    ) {
        viewModelScope.launch {
            execute(args)
                .flowOn(viewModelArgs.dispatcherIO)
                .catch {
                    if (it is HttpException) {
                        val failure = Failure(it, it.toMessage(), retry)
                        _error.postValue(failure)
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
