package com.tkuhn.recipefinder.ui.main.search

import androidx.lifecycle.SavedStateHandle
import com.tkuhn.recipefinder.repository.RecipesRepo
import com.tkuhn.recipefinder.ui.BaseViewModel
import com.tkuhn.recipefinder.utils.notNull
import kotlin.contracts.ExperimentalContracts

class SearchViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val recipesRepo: RecipesRepo
) : BaseViewModel() {

    val minCalories = savedStateHandle.getLiveData<String>("minCalories")
    val maxCalories = savedStateHandle.getLiveData<String>("maxCalories")

    @UseExperimental(ExperimentalContracts::class)
    fun search() {
        val min = minCalories.value?.toIntOrNull()
        val max = maxCalories.value?.toIntOrNull()

        if (notNull(min, max)) {
            hideKeyboard()
            load(recipesRepo.findRecipesBuNutrient(min, max))
        }
    }
}