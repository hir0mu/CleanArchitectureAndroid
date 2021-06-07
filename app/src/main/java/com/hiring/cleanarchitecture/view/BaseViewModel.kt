package com.hiring.cleanarchitecture.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiring.cleanarchitecture.R
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel: ViewModel() {

    private val _error: MutableLiveData<Failure> = MutableLiveData()
    val error: LiveData<Failure> = _error

    protected fun execute(call: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                call()
            } catch (e: HttpException) {
                val failure = Failure(e, e.toMessage()) { execute(call) }
                _error.postValue(failure)
            }
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
