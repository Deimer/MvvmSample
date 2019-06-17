package co.com.ceiba.mobile.pruebadeingreso.util

import co.com.ceiba.mobile.pruebadeingreso.MyApplication

class RUtil {

    companion object {
        fun R_string(resId: Int): String {
            return MyApplication.getInstance().getString(resId)
        }
    }
}