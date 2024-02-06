import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
//import com.example.tutorapp395project.ui.screens.MainActivity
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tutorapp395project", appContext.packageName)


    }
}

// Testing the landing page

//@RunWith(AndroidJUnit4::class)
//class MainActivityTest {
//
//    @Before
//    fun setUp() {
//        // Start the MainActivity
//        ActivityScenario.launch(MainActivity::class.java)
//    }
//
//    @After
//    fun tearDown() {
//        // Finish the activity after each test
//        ActivityScenario.launch(MainActivity::class.java).close()
//    }
//
//}
