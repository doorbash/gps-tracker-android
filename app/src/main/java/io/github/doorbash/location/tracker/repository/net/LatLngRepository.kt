package io.github.doorbash.location.tracker.repository.net

import android.util.Log
import io.github.doorbash.location.tracker.BASE_URL
import io.github.doorbash.location.tracker.model.LatLng
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import ru.gildor.coroutines.okhttp.await
import java.lang.Exception
import java.util.*

object LatLngRepository {
    var client: OkHttpClient = OkHttpClient()

    // todo: add basic auth
    public suspend fun getList(deviceId: String): List<LatLng> {
        val request: Request = Request.Builder()
            .url("$BASE_URL/list?did=$deviceId")
            .build()
        val response = client.newCall(request).await()
        val body = response.body!!.string()
        Log.d(javaClass.simpleName, body)
        if(body == "ERROR") {
            return mutableListOf()
        }
        val jsonArray = try { JSONArray(body) } catch (e: Exception) {
            return mutableListOf()
        }
        val list = mutableListOf<LatLng>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                LatLng(
                    deviceId,
                    Date(jsonObject.getLong("datetime") * 1000),
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lng"),
                    jsonObject.getDouble("alt"),
                    jsonObject.getDouble("hdop"),
                    jsonObject.getDouble("pdop"),
                    jsonObject.getDouble("vdop"),
                )
            )
        }
        return list
    }
}