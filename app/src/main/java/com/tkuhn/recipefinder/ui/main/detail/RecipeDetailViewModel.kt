package com.tkuhn.recipefinder.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.tkuhn.recipefinder.repository.RecipesRepo
import com.tkuhn.recipefinder.ui.BaseViewModel

class RecipeDetailViewModel(
    private val recipeId: Long,
    private val recipesRepo: RecipesRepo
) : BaseViewModel() {

    val recipeDetail = MutableLiveData<UiRecipeDetail>()
    val recipeSummary = MutableLiveData<String>()

    init {
        loadRecipeDetail()
        loadRecipeSummary()
    }

    private fun loadRecipeDetail() {
        load(recipesRepo.getRecipeDetail(recipeId), CALL_DETAIL, onData = {
            recipeDetail.value = UiRecipeDetail.create(it)
        })
    }

    private fun loadRecipeSummary() {
        load(recipesRepo.getRecipeSummary(recipeId), CALL_SUMMARY, onData = {
            recipeSummary.value = it.summary
        })
    }

    companion object {
        const val CALL_DETAIL = 1
        const val CALL_SUMMARY = 1
    }
}