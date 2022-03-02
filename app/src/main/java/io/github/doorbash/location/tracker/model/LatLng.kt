package io.github.doorbash.location.tracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(primaryKeys = ["device_id", "datetime"])
data class LatLng(
    @ColumnInfo(name = "device_id") val deviceId: String,
    @ColumnInfo(name = "datetime") val datetime: Date,
    @ColumnInfo(name = "lat") val lat: Double?,
    @ColumnInfo(name = "lng") val lng: Double?,
    @ColumnInfo(name = "alt") val alt: Double?,
    @ColumnInfo(name = "hdop") val hdop: Double?,
    @ColumnInfo(name = "pdop") val pdop: Double?,
    @ColumnInfo(name = "vdop") val vdop: Double?,
) {
    override fun equals(other: Any?) = other is LatLng && datetime == other.datetime
}