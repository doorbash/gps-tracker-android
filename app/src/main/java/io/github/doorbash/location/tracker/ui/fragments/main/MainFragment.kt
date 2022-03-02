package io.github.doorbash.location.tracker.ui.fragments.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.doorbash.location.tracker.R
import io.github.doorbash.location.tracker.model.LatLng
import io.github.doorbash.location.tracker.ui.adapters.LatLngAdapter


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var listView: ListView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        listView = v.findViewById(android.R.id.list)
        listView.adapter = LatLngAdapter(context, ArrayList())
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                val latlng = listView.adapter.getItem(index) as LatLng
                // val gmmIntentUri = Uri.parse("geo:${latlng.lat},${latlng.lng}?z=16")
                val gmmIntentUri = Uri.parse("geo:${latlng.lat},${latlng.lng}?q=${latlng.lat},${latlng.lng}(${latlng.deviceId} - ${latlng.datetime})")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)

            }

        swipeRefreshLayout = v.findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.latLngList.observe(viewLifecycleOwner) { list ->
            with((listView.adapter as LatLngAdapter)) {
                Log.d(javaClass.simpleName, "observe(): list: $list")
                var c = 0
                // az jadid tarin be ghadimi tarin
                for (i in 0 until list.size) {
                    val item = list[i]
                    if (items.contains(item)) {
                        break
                    }
                    items.add(c++, item)
                }
                notifyDataSetChanged()
            }
            swipeRefreshLayout.isRefreshing = false
        }

        v.post {
            // (activity as AppCompatActivity).supportActionBar!!.title = "Location Tracker ($DEVICE_ID)"
            swipeRefreshLayout.isRefreshing = true
            viewModel.refresh()
        }


        return v
    }

}