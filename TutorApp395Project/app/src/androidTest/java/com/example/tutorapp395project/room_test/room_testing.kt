import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tutorapp395project.data.AppDatabase
import com.example.tutorapp395project.data.Grade
import com.example.tutorapp395project.data.databaseDao
import com.example.tutorapp395project.data.userTable
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class DatabaseDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var databaseDao: databaseDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        databaseDao = db.databaseDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertUser() = runBlocking {
        // Create a user to insert
        val user = userTable(
            id = 1,
            first_name = "John",
            last_name = "Doe",
            date_of_birth = Date(),
            grade_level = 10,
            school = "Sample School",
            email = "john.doe@example.com",
            password = "password123",
            role = "student"
        )

        // Insert the user into the test database
        databaseDao.addUser(user)

        // Retrieve the user from the test database
        val insertedUser = databaseDao.getUserById(user.id)

        // Verify that the inserted user matches the expected user
        assertEquals(user, insertedUser)
    }
}
