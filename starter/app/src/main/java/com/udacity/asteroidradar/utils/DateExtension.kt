package com.udacity.asteroidradar.utils

import android.util.Log
import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.*

fun Date.toQueryString(): String {
    return try {
        val simpleDateFormat =
            SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        simpleDateFormat.format(this)
    } catch (e: java.lang.Exception) {
        return ""
    }
}
