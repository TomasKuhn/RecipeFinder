
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.common.truth.Subject
import com.google.common.truth.Truth
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.databinding.DataViewHolder
import com.tkuhn.recipefinder.utils.extensions.toText
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf

infix fun Int.perform(action: ViewAction): ViewInteraction {
    return onView(withId(this)).perform(action)
}

infix fun Int.matches(matcher: Matcher<View>): ViewInteraction {
    return onView(withId(this)).check(ViewAssertions.matches(matcher))
}

inline fun <reified F : Fragment> launchFragment(args: Bundle? = null): FragmentScenario<F> {
    return launchFragmentInContainer<F>(themeResId = R.style.AppTheme, fragmentArgs = args)
}

infix fun Int.hasText(text: String?): ViewInteraction {
    return matches(withText(text))
}

infix fun Int.hasHint(resourceId: Int): ViewInteraction {
    return matches(withHint(resourceId))
}

infix fun Int.hasToolbarTitle(title: String?): ViewInteraction? {
    return onView(allOf(instanceOf(TextView::class.java), withParent(withId(this))))
        .check(ViewAssertions.matches(withText(title)))
}

infix fun Int.write(text: String?): ViewInteraction {
    return perform(typeText(text))
}

infix fun Int.clickOnRecyclerItem(position: Int): ViewInteraction {
    return perform(actionOnItemAtPosition<DataViewHolder<*>>(position, click()))
}

infix fun Int.hasErrorText(error: String?) {
    matches(CustomMatchers.hasErrorText(error))
}

fun Int.itemMatches(position: Int, targetViewId: Int, itemMatcher: Matcher<View>) {
    perform(scrollToPosition<DataViewHolder<*>>(position))
        .check(ViewAssertions.matches(matchRecyclerItemOnPosition(position, itemMatcher, targetViewId)))
}

infix fun Any?.assert(block: Subject.() -> Unit) {
    Truth.assertThat(this).run {
        block(this)
    }
}

fun snackbarIsDisplayed(snackbarMessage: String): ViewInteraction {
    return onView(allOf(withId(R.id.snackbar_text), withText(snackbarMessage)))
        .check(ViewAssertions.matches(isDisplayed()))
}

fun snackbarIsDisplayed(snackbarMessage: Int): ViewInteraction {
    return snackbarIsDisplayed(snackbarMessage.toText())
}

private fun matchRecyclerItemOnPosition(position: Int, itemMatcher: Matcher<View>, targetViewId: Int): Matcher<View?> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("RecyclerView item on position $position $itemMatcher")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val targetView = viewHolder?.itemView?.findViewById<View>(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}