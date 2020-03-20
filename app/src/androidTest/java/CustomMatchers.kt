import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

object CustomMatchers {

    fun hasErrorText(error: String? = null): BoundedMatcher<View, TextInputLayout> {
        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Has error text")
                if (error != null) {
                    description.appendText(": $error")
                }
            }

            override fun matchesSafely(item: TextInputLayout): Boolean {
                return item.error != null && (error == null || error == item.error)
            }
        }
    }

    fun isNotEmpty(): BoundedMatcher<View, RecyclerView> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("RecyclerView is empty")
            }

            override fun matchesSafely(item: RecyclerView): Boolean {
                return item.adapter?.itemCount ?: 0 > 0
            }
        }
    }
}