package com.gitficko.github.app

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.View
import com.gitficko.github.remote.Networking
import timber.log.Timber

class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Networking.init(this)
        Timber.plant(Timber.DebugTree())
    }
}