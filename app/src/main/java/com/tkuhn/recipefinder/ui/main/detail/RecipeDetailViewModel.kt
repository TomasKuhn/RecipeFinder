package com.tkuhn.recipefinder.ui.main.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.tkuhn.recipefinder.domain.Ingredient
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.repository.RecipesRepo
import com.tkuhn.recipefinder.ui.BaseViewModel

class RecipeDetailViewModel(
    private val recipeId: Long,
    private val recipesRepo: RecipesRepo
) : BaseViewModel() {

    private val recipeDetail = MutableLiveData<RecipeDetail>()
    val uiRecipeDetail = recipeDetail.map {
        UiRecipeDetail.create(it)
    }
    val ingredients = recipeDetail.map {
        it.ingredients
    }
    val ingredientsMapper: (Ingredient) -> String = {
        it.name
    }
    val recipeSummary = MutableLiveData<String>()
    val isRefreshing = MutableLiveData<Boolean>()

    init {
        loadRecipeDetail()
        loadRecipeSummary()
    }

    fun refresh() {
        recipesRepo.refreshRecipeDetail()?.let {
            load(it, CALL_DETAIL, isRefreshing, onData = { data ->
                recipeDetail.value = data
            })
        }

        recipesRepo.refreshRecipeSummary()?.let {
            load(it, CALL_DETAIL, isRefreshing, onData = { data ->
                recipeSummary.value = data.summary
            })
        }
    }

    private fun loadRecipeDetail() {
        load(recipesRepo.getRecipeDetail(recipeId), CALL_DETAIL, onData = {
            recipeDetail.value = it
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