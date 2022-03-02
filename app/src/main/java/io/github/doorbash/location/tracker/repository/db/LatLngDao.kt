package io.github.doorbash.location.tracker.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.doorbash.location.tracker.DEVICE_ID
import io.github.doorbash.location.tracker.model.LatLng

@Dao
interface LatLngDao {
    @Query("SELECT * FROM LatLng where device_id = :deviceId order by datetime desc limit 100")
    fun getAll(deviceId: String): List<LatLng>

    @Insert
    fun insertAll(latLngList: List<LatLng>)
}
