package com.tkuhn.recipefinder.di

import androidx.lifecycle.SavedStateHandle
import com.tkuhn.recipefinder.domain.Forecast
import com.tkuhn.recipefinder.ui.main.MainActivityViewModel
import com.tkuhn.recipefinder.ui.main.detail.DetailViewModel
import com.tkuhn.recipefinder.ui.main.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { ListViewModel(get()) }
    viewModel { (handle: SavedStateHandle, forecast: Forecast) ->
        DetailViewModel(
            handle,
            forecast
        )
    }
}