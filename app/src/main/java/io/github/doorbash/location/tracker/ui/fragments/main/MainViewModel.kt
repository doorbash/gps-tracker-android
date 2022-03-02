package io.github.doorbash.location.tracker.ui.fragments.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.doorbash.location.tracker.DEVICE_ID
import io.github.doorbash.location.tracker.model.LatLng
import io.github.doorbash.location.tracker.repository.db.Database
import io.github.doorbash.location.tracker.repository.net.LatLngRepository
import io.github.doorbash.location.tracker.ui.plusAssign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var latLngList = MutableLiveData<MutableList<LatLng>>()

    fun getAddSublist(list: List<LatLng>): List<LatLng> {
        val lastDateTime =
            if (latLngList.value == null || latLngList.value!!.isEmpty()) Date(0) else latLngList.value!![0].datetime
        var index = -1
        // az ghadimi tarin be jadid tarin
        for (i in list.size - 1 downTo 0) {
            val item = list[i]
            if (item.datetime.after(lastDateTime)) {
                index = i
                break
            }
        }
        return list.subList(0, index + 1) // az jadid tarin be ghadimi tarin
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            var list = getAddSublist(Database.db!!.latlngDao().getAll(DEVICE_ID))
            Log.d(javaClass.simpleName, "list from db: $list")
            withContext(Dispatchers.Main){
                latLngList += list
            }
            list = getAddSublist(LatLngRepository.getList(DEVICE_ID))
            Log.d(javaClass.simpleName, "list from server: $list")
            Database.db!!.latlngDao().insertAll(list.reversed())
            withContext(Dispatchers.Main){
                latLngList += list
            }
        }
    }
}