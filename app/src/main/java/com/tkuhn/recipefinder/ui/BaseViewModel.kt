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

    protected fun <T> load(
        input: Flow<Resource<T>>,
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
                        if (!onBackground) {
                            isLoading.value = false
                        }
                        onSuccess?.invoke()
                    }
                    is Resource.Error -> {
                        val error = resource.error
                        Timber.d("Error because of $error")
                        if (!onBackground) {
                            snackMessage.value = error.message
                            isLoading.value = false
                        }
                        onError?.invoke(resource.error)
                    }
                    is Resource.Loading -> {
                        Timber.d("Loading...")
                        if (!onBackground) {
                            isLoading.value = true
                        }
                    }
                }

                resource.data?.let { data ->
                    onData?.invoke(data)
                }
            }
        }
    }

}
