import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.extensions.toText
import org.hamcrest.Matcher
import org.hamcrest.Matchers

infix fun Int.perform(action: ViewAction) {
    onView(ViewMatchers.withId(this)).perform(action)
}

infix fun Int.matches(matcher: Matcher<View>) {
    onView(ViewMatchers.withId(this)).check(ViewAssertions.matches(matcher))
}

inline fun <reified F : Fragment> launchFragment() {
    launchFragmentInContainer<F>(themeResId = R.style.AppTheme)
}

infix fun Int.hasText(text: String) {
    matches(ViewMatchers.withText(text))
}

infix fun Int.hasHint(resourceId: Int) {
    matches(ViewMatchers.withHint(resourceId))
}

infix fun Int.write(text: String) {
    perform(ViewActions.typeText(text))
}

fun snackbarIsDisplayed(snackbarMessage: String) {
    onView(Matchers.allOf(ViewMatchers.withId(R.id.snackbar_text), ViewMatchers.withText(snackbarMessage))).check(
        ViewAssertions.matches(
            ViewMatchers.isDisplayed()
        )
    )
}

fun snackbarIsDisplayed(snackbarMessage: Int) {
    snackbarIsDisplayed(snackbarMessage.toText())
}