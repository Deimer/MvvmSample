package co.com.ceiba.mobile.pruebadeingreso

import co.com.ceiba.mobile.pruebadeingreso.data.interactors.UserInteractor
import org.junit.rules.TemporaryFolder
import org.mockito.Mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserUnitTest: BaseTest() {

    @get:Rule
    var tempFolder = TemporaryFolder()

    @Mock
    lateinit var userInteractor: UserInteractor

    @Before
    fun setupTest() {
        userInteractor = UserInteractor()
    }

    @Test
    fun getUsers() {
        userInteractor.getUsers()?.subscribe({
            if(it.isNotEmpty()) {
                assert(true)
            } else {
                assert(false)
            }
        }, {
            assert(false)
        })
    }
}