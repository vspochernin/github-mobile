package com.gitficko.github.utils

import com.gitficko.github.model.TimeUnit

fun formatSecondsToTimePassed(seconds: Long): String {
    if (seconds / TimeUnit.YEAR.seconds != 0L) {
        return "${seconds / TimeUnit.YEAR.seconds}y"
    }
    if (seconds / TimeUnit.MONTH.seconds != 0L) {
        return "${seconds / TimeUnit.MONTH.seconds}m"
    }
    if (seconds / TimeUnit.WEEK.seconds != 0L) {
        return "${seconds / TimeUnit.WEEK.seconds}w"
    }
    if (seconds / TimeUnit.DAY.seconds != 0L) {
        return "${seconds / TimeUnit.DAY.seconds}d"
    }
    if (seconds / TimeUnit.HOUR.seconds != 0L) {
        return "${seconds / TimeUnit.HOUR.seconds}h"
    }
    if (seconds / TimeUnit.MINUTE.seconds != 0L) {
        return "${seconds / TimeUnit.HOUR.seconds}min"
    }
    return "${seconds / TimeUnit.HOUR.seconds}s"
}
