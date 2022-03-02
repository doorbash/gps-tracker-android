package io.github.doorbash.location.tracker.repository.db

import android.content.Context
import androidx.room.Room

object Database {
    var db: AppDatabase? = null

    fun init(c: Context) {
        db = Room.databaseBuilder(c, AppDatabase::class.java, "db").fallbackToDestructiveMigration().build()
    }
}