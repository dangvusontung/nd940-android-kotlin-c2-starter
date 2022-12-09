package com.udacity.asteroidradar.utils

import android.util.Log
import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.*

fun String.toTimestamp(): Long {
    val simpleDateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    val date = simpleDateFormat.parse(this)
    return date?.time ?: 0L
}

