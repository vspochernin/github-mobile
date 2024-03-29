package com.gitficko.github.app

import android.app.Application
import com.gitficko.github.remote.CachedClient
import com.gitficko.github.remote.Networking
import timber.log.Timber

class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Networking.init(this)
        CachedClient.init(this)
        Timber.plant(Timber.DebugTree())
    }
}