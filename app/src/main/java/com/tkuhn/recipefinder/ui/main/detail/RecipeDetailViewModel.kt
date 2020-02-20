package com.tkuhn.recipefinder.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.repository.RecipesRepo
import com.tkuhn.recipefinder.ui.BaseViewModel

class RecipeDetailViewModel(
    private val recipeId: Long,
    private val recipesRepo: RecipesRepo
) : BaseViewModel() {

    val recipeDetail = MutableLiveData<RecipeDetail>()

    init {
        loadRecipeDetail()
    }

    private fun loadRecipeDetail() {
        load(recipesRepo.getRecipeDetail(recipeId), onData = {
            recipeDetail.value = it
        })
    }
}