package uz.gita.newsappmn.util

import android.util.Log

fun logger(message:String, tag:String = "TTT"){
    Log.d(tag, message)
}
//2023-06-20T17:00:02Z
fun getHour(time: String): String {
    return time.substring(11, 16)
}

fun getDate(time: String): String {
    return time.substring(0, 10)
}