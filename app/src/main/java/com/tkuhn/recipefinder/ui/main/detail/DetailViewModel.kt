package com.tkuhn.recipefinder.ui.main.detail

import androidx.lifecycle.SavedStateHandle
import com.tkuhn.recipefinder.domain.Forecast
import com.tkuhn.recipefinder.ui.BaseViewModel

class DetailViewModel(
    handle: SavedStateHandle,
    forecast: Forecast
) : BaseViewModel() {

    val editText = handle.getLiveData<String>("edit", forecast.message)
}