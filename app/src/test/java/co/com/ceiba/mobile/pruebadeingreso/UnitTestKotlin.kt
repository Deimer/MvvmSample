package co.com.ceiba.mobile.pruebadeingreso

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Test
import java.util.*

class UnitTestKotlin {

    @Test
    fun testRxAndroid() {
        createFromArray().subscribe { arr ->
            println("Received array is: ${Arrays.toString(arr)}")
        }

        createFromIterable().subscribe { item ->
            println("Received item is: $item")
        }

        createRange().subscribe { item ->
            println("Received item is: $item")
        }
    }

    private fun showJustJob() {
        val dataStream = Observable.just(10, 20, 30, 40)

        val dataObserver = object: Observer<Int> {
            override fun onComplete() {
                println("All data is received...")
            }
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(t: Int) {
                println("New data is received: $t")
            }
            override fun onError(e: Throwable) {
                println("Error: ${e.message}")
            }
        }

        dataStream.subscribe(dataObserver)
    }

    private fun createFromArray(): Observable<Array<Int>>{
        return Observable.fromArray(arrayOf(1, 5, 7, 9))
    }

    private fun createFromIterable(): Observable<Int>{
        return Observable.fromIterable(mutableListOf(2, 5, 7))
    }

    private fun createRange(): Observable<Int>{
        return Observable.range(1, 3).repeat(3)
    }
}