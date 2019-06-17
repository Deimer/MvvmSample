package co.com.ceiba.mobile.pruebadeingreso

import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open abstract class BaseTest {

    protected fun <T> anyObject(): T {
        return Mockito.any<T>()
    }
}