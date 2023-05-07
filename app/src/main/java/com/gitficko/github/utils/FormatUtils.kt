package com.gitficko.github.utils

import com.gitficko.github.model.TimeUnit

fun formatMillisToTimePassed(millis: Long): String {
    if (millis / TimeUnit.YEAR.millis != 0L) {
        return "${millis / TimeUnit.YEAR.millis}y"
    }
    if (millis / TimeUnit.MONTH.millis != 0L) {
        return "${millis / TimeUnit.MONTH.millis}m"
    }
    if (millis / TimeUnit.WEEK.millis != 0L) {
        return "${millis / TimeUnit.WEEK.millis}w"
    }
    if (millis / TimeUnit.DAY.millis != 0L) {
        return "${millis / TimeUnit.DAY.millis}d"
    }
    if (millis / TimeUnit.HOUR.millis != 0L) {
        return "${millis / TimeUnit.HOUR.millis}h"
    }
    if (millis / TimeUnit.MINUTE.millis != 0L) {
        return "${millis / TimeUnit.HOUR.millis}min"
    }
    return "${millis / TimeUnit.HOUR.millis}s"
}
