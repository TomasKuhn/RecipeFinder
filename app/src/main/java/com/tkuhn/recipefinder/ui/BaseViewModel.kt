package com.tkuhn.recipefinder.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.ResourceError
import com.tkuhn.recipefinder.utils.LiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    val snackMessage = LiveEvent<String>()
    val isLoading = MutableLiveData<Boolean>()
    val hideKeyboardEvent = LiveEvent<Void>()
    private val loadDataIds = mutableSetOf<Int>()

    protected fun <T> load(
        input: Flow<Resource<T>>,
        id: Int,
        onSuccess: (() -> Unit)? = null,
        onError: ((error: ResourceError) -> Unit)? = null,
        onData: ((data: T) -> Unit)? = null,
        onBackground: Boolean = false
    ) {
        viewModelScope.launch {
            input.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Timber.d("Success with data data: ${resource.data}")
                        onSuccess?.invoke()
                        loadFinished(id, onBackground)
                    }
                    is Resource.Error   -> {
                        val error = resource.error
                        Timber.d("Error because of $error")
                        if (!onBackground) {
                            snackMessage.value = error.message
                        }
                        onError?.invoke(resource.error)
                        loadFinished(id, onBackground)
                    }
                    is Resource.Loading -> {
                        Timber.d("Loading...")
                        if (!onBackground) {
                            isLoading.value = true
                            loadDataIds.add(id)
                        }
                    }
                }

                resource.data?.let { data ->
                    onData?.invoke(data)
                }
            }
        }
    }

    protected fun hideKeyboard() {
        hideKeyboardEvent.call()
    }

    private fun loadFinished(id: Int, onBackground: Boolean) {
        if (!onBackground) {
            loadDataIds.remove(id)
            if (loadDataIds.isEmpty()) {
                isLoading.value = false
            }
        }
    }
}
