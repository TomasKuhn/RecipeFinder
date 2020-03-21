package com.tkuhn.recipefinder.ui.main.search

import CustomMatchers.hasErrorText
import CustomMatchers.isNotEmpty
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.extensions.toText
import hasHint
import hasText
import launchFragment
import matches
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import perform
import write

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

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
        onView(allOf(withId(R.id.snackbar_text), withText(R.string.search_min_max_condition))).check(matches(isDisplayed())) //TODO nicer
    }

    @Test
    fun loadRecipes() {
        launchFragment<SearchFragment>()
        R.id.vEditMinCalories write "1"
        R.id.vEditMaxCalories write "10"
        R.id.vButtonSearch perform click()
        SystemClock.sleep(2000) //TODO Implement idling resources https://medium.com/@elye.project/instrumental-test-better-espresso-without-sleep-d3391b19a581
        R.id.vLabelRecipes matches isDisplayed()
        R.id.vRecyclerRecipes matches isNotEmpty()
    }
}