package io.github.doorbash.location.tracker.repository.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import io.github.doorbash.location.tracker.model.LatLng;

@Database(entities = {LatLng.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract LatLngDao latlngDao();
}