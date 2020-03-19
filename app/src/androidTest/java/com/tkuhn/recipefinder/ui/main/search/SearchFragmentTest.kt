package com.tkuhn.recipefinder.ui.main.search

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tkuhn.recipefinder.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @Test
    fun launchFragmentAndVerifyUI() {
        val scenario = launchFragmentInContainer<SearchFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.vButtonSearch)).check(matches(isDisplayed()))
    }
}