package com.tkuhn.recipefinder.di

import androidx.lifecycle.SavedStateHandle
import com.tkuhn.recipefinder.ui.main.MainActivityViewModel
import com.tkuhn.recipefinder.ui.main.detail.RecipeDetailViewModel
import com.tkuhn.recipefinder.ui.main.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { (handle: SavedStateHandle) -> SearchViewModel(handle, get()) }
    viewModel { (recipeId: Long) -> RecipeDetailViewModel(recipeId) }
}