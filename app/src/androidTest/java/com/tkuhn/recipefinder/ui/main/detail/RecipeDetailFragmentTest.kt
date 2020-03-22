package com.tkuhn.recipefinder.ui.main.detail

import BaseUiTest
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.tkuhn.recipefinder.R
import hasText
import hasToolbarTitle
import itemMatches
import launchFragment
import org.junit.Before
import org.junit.Test


class RecipeDetailFragmentTest : BaseUiTest() {

    private lateinit var scenario: FragmentScenario<RecipeDetailFragment>

    @Before
    fun launchSearchFragment() {
        val args = bundleOf(
                "recipeId" to 30078L
        )
        scenario = launchFragment(args)
    }

    @Test
    fun launchFragmentAndVerifyUI() {
        lateinit var fragment: RecipeDetailFragment
        scenario.onFragment {
            fragment = it
        }
        val recipeDetail = fragment.vm.uiRecipeDetail.value
        val ingredients = fragment.vm.ingredients.value
        R.id.vToolbarRecipeDetail hasToolbarTitle recipeDetail?.title
        R.id.vTextDuration hasText recipeDetail?.duration
        R.id.vTextLikes hasText recipeDetail?.likes
        R.id.vTextRating hasText recipeDetail?.score
        ingredients?.forEachIndexed { index, ingredient ->
            R.id.vRecyclerIngredients.itemMatches(index, R.id.vTextIngredientName, withText(ingredient.name))
        }
    }
}