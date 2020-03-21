import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.tkuhn.recipefinder.R
import org.hamcrest.Matcher

fun Int.perform(action: ViewAction) {
    onView(ViewMatchers.withId(this)).perform(action)
}

fun Int.matches(matcher: Matcher<View>) {
    onView(ViewMatchers.withId(this)).check(ViewAssertions.matches(matcher))
}

inline fun <reified F : Fragment> launchFragment() {
    launchFragmentInContainer<F>(themeResId = R.style.AppTheme)
}