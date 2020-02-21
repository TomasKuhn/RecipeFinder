package com.tkuhn.recipefinder.ui.main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.repository.RecipesRepo
import com.tkuhn.recipefinder.ui.BaseViewModel
import com.tkuhn.recipefinder.utils.LiveEvent
import com.tkuhn.recipefinder.utils.extensions.toText
import com.tkuhn.recipefinder.utils.notNull
import kotlin.contracts.ExperimentalContracts

class SearchViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val recipesRepo: RecipesRepo
) : BaseViewModel() {

    val minCalories = savedStateHandle.getLiveData<String>("minCalories")
    val maxCalories = savedStateHandle.getLiveData<String>("maxCalories")
    val recipes = MutableLiveData<List<Recipe>>()
    val noRecipesFound = recipes.map { it?.isEmpty() == true }
    val onItemClick: (Recipe) -> Unit = {
        showRecipeDetailEvent.value = it
    }

    val showRecipeDetailEvent = LiveEvent<Recipe>()

    @UseExperimental(ExperimentalContracts::class)
    fun search() {
        val min = minCalories.value?.toIntOrNull()
        val max = maxCalories.value?.toIntOrNull()

        if (notNull(min, max)) {
            if (max > min) {
                hideKeyboard()
                load(recipesRepo.findRecipesBuNutrient(min, max), onData = {
                    recipes.value = it
                })
            } else {
                snackMessage.value = R.string.search_min_max_condition.toText()
            }
        } else {
            snackMessage.value = "Fill min and max"
        }
    }
}