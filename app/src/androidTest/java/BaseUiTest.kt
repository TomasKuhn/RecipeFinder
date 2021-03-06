import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tkuhn.recipefinder.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest {

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.resources)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.resources)
    }
}