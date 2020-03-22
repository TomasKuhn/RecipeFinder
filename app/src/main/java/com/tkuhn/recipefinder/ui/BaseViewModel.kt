package com.tkuhn.recipefinder.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tkuhn.recipefinder.EspressoIdlingResources
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.ResourceError
import com.tkuhn.recipefinder.utils.LiveEvent
import com.tkuhn.recipefinder.utils.extensions.setDistinctValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

open class BaseViewModel : ViewModel() {

    val snackMessage = LiveEvent<String>()
    val isLoading = MutableLiveData<Boolean>()
    val hideKeyboardEvent = LiveEvent<Void>()
    private val loadDataIds = mutableMapOf<MutableLiveData<Boolean>, MutableSet<Int>>()

    protected fun <T> load(
            input: Flow<Resource<T>>,
            id: Int,
            loading: MutableLiveData<Boolean> = isLoading,
            onSuccess: (() -> Unit)? = null,
            onError: ((error: ResourceError) -> Unit)? = null,
            onData: ((data: T) -> Unit)? = null,
            onBackground: Boolean = false
    ) {
        viewModelScope.launch {
            EspressoIdlingResources.increment()
            val espressoDecremented = AtomicBoolean()
            input.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Timber.d("Success with data data: ${resource.data}")
                        onSuccess?.invoke()
                        if (!onBackground) {
                            onIsLoadingChange(id, loading, false)
                        }
                        if (espressoDecremented.compareAndSet(false, true)) {
                            EspressoIdlingResources.decrement()
                        }
                    }
                    is Resource.Error   -> {
                        val error = resource.error
                        Timber.d("Error because of $error")
                        onError?.invoke(resource.error)
                        if (!onBackground) {
                            snackMessage.value = error.message
                            onIsLoadingChange(id, loading, false)
                        }
                        if (espressoDecremented.compareAndSet(false, true)) {
                            EspressoIdlingResources.decrement()
                        }
                    }
                    is Resource.Loading -> {
                        Timber.d("Loading...")
                        if (!onBackground) {
                            onIsLoadingChange(id, loading, true)
                        }
                    }
                }

                resource.data?.let { data ->
                    onData?.invoke(data)
                }
            }
        }
    }

    private fun onIsLoadingChange(
            id: Int,
            loading: MutableLiveData<Boolean>,
            isInProgress: Boolean
    ) {
        if (isInProgress) {
            loadDataIds.getOrPut(loading, { mutableSetOf() }).add(id)
            loading.setDistinctValue(true)
        } else {
            loadDataIds[loading]?.let {
                it.remove(id)
                if (it.isEmpty()) {
                    loadDataIds.remove(loading)
                    loading.setDistinctValue(false)
                }
            }
        }
    }

    protected fun hideKeyboard() {
        hideKeyboardEvent.call()
    }
}
