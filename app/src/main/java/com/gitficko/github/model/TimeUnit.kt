package com.gitficko.github.model

enum class TimeUnit(val seconds: Long) {
    MINUTE(60),
    HOUR(MINUTE.seconds * 60),
    DAY(HOUR.seconds * 24),
    WEEK(DAY.seconds * 7),
    MONTH(DAY.seconds * 30),
    YEAR(DAY.seconds * 365)
}
