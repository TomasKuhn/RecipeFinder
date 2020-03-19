import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

@DisplayName("Example unit tests")
class ExampleUnitTest {

    @BeforeAll
    fun initAll() {
    }

    @BeforeEach
    fun init() {
    }

    @DisplayName("Test addition assertion")
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @RepeatedTest(
        value = 5,
        name = "Repeating {currentRepetition} von {totalRepetitions}"
    )
    fun repeated() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun tstViewModel() {
    }
}