package com.gitficko.github.model

enum class TimeUnit(val millis: Long) {
    SECOND(1000),
    MINUTE(SECOND.millis * 60),
    HOUR(MINUTE.millis * 60),
    DAY(HOUR.millis * 24),
    WEEK(DAY.millis * 7),
    MONTH(DAY.millis * 30),
    YEAR(DAY.millis * 365)
}
