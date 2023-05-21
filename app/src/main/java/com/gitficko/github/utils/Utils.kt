package com.gitficko.github.utils

import androidx.navigation.NavOptions
import com.gitficko.github.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class Utils {

    companion object {
        fun getTimeAgo(dateString: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date = sdf.parse(dateString)
            val time = date?.time ?: 0
            val now = System.currentTimeMillis()

            val diffInSeconds = abs(now - time) / 1000
            val diffInMinutes = diffInSeconds / 60
            val diffInHours = diffInMinutes / 60
            val diffInDays = diffInHours / 24
            val diffInWeeks = diffInDays / 7
            val diffInMonths = diffInDays / 30
            val diffInYears = diffInMonths / 12

            return when {
                diffInYears > 0 -> "Updated $diffInYears years ago"
                diffInMonths > 0 -> "Updated $diffInMonths months ago"
                diffInWeeks > 0 -> "Updated $diffInWeeks weeks ago"
                diffInDays > 0 -> "Updated $diffInDays days ago"
                diffInHours > 0 -> "Updated $diffInHours hours ago"
                diffInMinutes > 0 -> "Updated $diffInMinutes minutes ago"
                else -> "Updated just now"
            }
        }

        val navOptionsToLeft = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_left)
            .build()

        val navOptionsToRight = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
    }
}