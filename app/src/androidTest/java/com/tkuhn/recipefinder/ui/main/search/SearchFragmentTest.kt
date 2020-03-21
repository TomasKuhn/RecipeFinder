package com.tkuhn.recipefinder.ui.main.search

import BaseUiTest
import CustomMatchers.hasErrorText
import CustomMatchers.isNotEmpty
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
        R.id.vEditMinCalories write "1"
        R.id.vEditMaxCalories write "10"
        R.id.vButtonSearch perform click()
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
}