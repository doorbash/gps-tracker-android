package io.github.doorbash.location.tracker

import android.app.Application
import io.github.doorbash.location.tracker.repository.db.Database

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Database.init(applicationContext)
    }
}