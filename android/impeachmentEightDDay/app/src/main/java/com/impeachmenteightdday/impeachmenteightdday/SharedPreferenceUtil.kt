package com.impeachmenteightdday.impeachmenteightdday

import android.app.Activity
import android.content.Context

class SharedPreferenceUtil {
    companion object {
        fun getdDayTime(context: Context) =
                context.getSharedPreferences("common", Activity.MODE_PRIVATE)?.getLong("dDayTime", System.currentTimeMillis() / 1000) ?: System.currentTimeMillis() / 1000

        fun setdDayTime(context: Context, dDayTime: Long) =
                context.getSharedPreferences("common", Activity.MODE_PRIVATE)?.edit()?.apply {
                    putLong("dDayTime", dDayTime)
                }?.commit()
    }
}