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
    val isRefreshing = MutableLiveData<Boolean>()

    init {
        loadRecipeDetail()
        loadRecipeSummary()
    }

    fun refresh() {
        recipesRepo.refreshRecipeDetail()?.let {
            load(it, CALL_DETAIL, isRefreshing, onData = { data ->
                recipeDetail.value = UiRecipeDetail.create(data)
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