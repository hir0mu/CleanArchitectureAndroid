package com.hiring.cleanarchitecture.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiring.cleanarchitecture.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel: ViewModel() {

    private val _error: MutableLiveData<Failure> = MutableLiveData()
    val error: LiveData<Failure> = _error

    protected fun <T> Flow<T>.execute(
        onSuccess: (T) -> Unit = {},
        retry: () -> Unit
    ) {
        viewModelScope.launch {
            // TODO: Dispatchersはinjectする
            flowOn(Dispatchers.IO)
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
