package com.tkuhn.recipefinder.ui.main.search

import BaseUiTest
import CustomMatchers.hasErrorText
import CustomMatchers.isNotEmpty
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import assert
import clickOnRecyclerItem
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.extensions.toText
import hasHint
import hasText
import launchFragment
import matches
import org.junit.Test
import perform
import snackbarIsDisplayed
import write

class SearchFragmentTest : BaseUiTest() {

    @Test
    fun launchFragmentAndVerifyUI() {
        launchFragment<SearchFragment>()
        R.id.vButtonSearch matches isDisplayed()
        R.id.vEditMinCalories hasHint R.string.search_min_calories
        R.id.vEditMaxCalories hasHint R.string.search_max_calories
        R.id.vEditMinCalories hasText ""
        R.id.vEditMaxCalories hasText ""
    }

    @Test
    fun showEmptyMinMaxErrors() {
        launchFragment<SearchFragment>()
        R.id.vButtonSearch perform click()
        R.id.vTilMinCalories matches hasErrorText(R.string.err_empty_field.toText())
        R.id.vTilMaxCalories matches hasErrorText(R.string.err_empty_field.toText())
    }

    @Test
    fun showMinMaxConditionError() {
        launchFragment<SearchFragment>()
        R.id.vEditMinCalories write "100"
        R.id.vEditMaxCalories write "1"
        R.id.vButtonSearch perform click()
        snackbarIsDisplayed(R.string.search_min_max_condition)
    }

    @Test
    fun loadRecipes() {
        launchFragment<SearchFragment>()
        searchWithValidRequirements()
        R.id.vLabelRecipes matches isDisplayed()
        R.id.vRecyclerRecipes matches isNotEmpty()
    }

    @Test
    fun showEmptyRecipesLabel() {
        launchFragment<SearchFragment>().onFragment { fragment ->
            fragment.vm.recipes.value = emptyList()
        }
        R.id.vLabelNoRecipes matches isDisplayed()
    }

    @Test
    fun showRecipeDetail() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            setGraph(R.navigation.nav_graph)
        }
        launchFragment<SearchFragment>().onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        searchWithValidRequirements()
        val recipePosition = 0
        R.id.vRecyclerRecipes.clickOnRecyclerItem(recipePosition)

        navController.currentDestination?.id assert { isEqualTo(R.id.detailFragment) }
    }

    private fun searchWithValidRequirements() {
        R.id.vEditMinCalories write "1"
        R.id.vEditMaxCalories write "10"
        R.id.vButtonSearch perform click()
    }
}