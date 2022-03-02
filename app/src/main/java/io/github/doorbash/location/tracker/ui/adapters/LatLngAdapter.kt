package io.github.doorbash.location.tracker.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.github.marlonlom.utilities.timeago.TimeAgo
import io.github.doorbash.location.tracker.R
import io.github.doorbash.location.tracker.model.LatLng

class LatLngAdapter(
    private val context: Context?,
    var items: MutableList<LatLng>
) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): LatLng {
        return items[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val ll = getItem(position)
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.item_latlng, parent, false)
            viewHolder.location = convertView.findViewById<View>(R.id.location) as TextView
            viewHolder.time = convertView.findViewById<View>(R.id.time) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.location!!.text = """
              ${ll!!.lat}, ${ll.lng}
              alt: ${ll.alt}
              hdop: ${ll.hdop}, pdop: ${ll.pdop}, vdop: ${ll.vdop}
              """.trimIndent()
        viewHolder.time!!.text = TimeAgo.using(ll.datetime.time)
        return convertView!!
    }

    private class ViewHolder {
        var location: TextView? = null
        var time: TextView? = null
    }
}