package com.tkuhn.recipefinder.ui.main.search

import CustomMatchers.hasErrorText
import CustomMatchers.isNotEmpty
import android.os.SystemClock
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.extensions.toText
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @Test
    fun launchFragmentAndVerifyUI() {
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.vButtonSearch)).check(matches(isDisplayed()))
        onView(withId(R.id.vEditMinCalories)).check(matches(withHint(R.string.search_min_calories)))
        onView(withId(R.id.vEditMaxCalories)).check(matches(withHint(R.string.search_max_calories)))
        onView(withId(R.id.vEditMinCalories)).check(matches(withText("")))
        onView(withId(R.id.vEditMaxCalories)).check(matches(withText("")))
    }

    @Test
    fun showEmptyMinMaxErrors() {
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.vButtonSearch)).perform(click())
        onView(withId(R.id.vTilMinCalories)).check(matches(hasErrorText(R.string.err_empty_field.toText())))
        onView(withId(R.id.vTilMaxCalories)).check(matches(hasErrorText(R.string.err_empty_field.toText())))
    }

    @Test
    fun showMinMaxConditionError() {
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.vEditMinCalories)).perform(typeText("100"))
        onView(withId(R.id.vEditMaxCalories)).perform(typeText("1"))
        onView(withId(R.id.vButtonSearch)).perform(click())
        onView(allOf(withId(R.id.snackbar_text), withText(R.string.search_min_max_condition))).check(matches(isDisplayed()))
    }

    @Test
    fun loadRecipes() {
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.vEditMinCalories)).perform(typeText("1"))
        onView(withId(R.id.vEditMaxCalories)).perform(typeText("10"))
        onView(withId(R.id.vButtonSearch)).perform(click())
        SystemClock.sleep(2000) //TODO Implement idling resources https://medium.com/@elye.project/instrumental-test-better-espresso-without-sleep-d3391b19a581
        onView(withId(R.id.vLabelRecipes)).check(matches(isDisplayed()))
        onView(withId(R.id.vRecyclerRecipes)).check(matches(isNotEmpty()))
    }
}