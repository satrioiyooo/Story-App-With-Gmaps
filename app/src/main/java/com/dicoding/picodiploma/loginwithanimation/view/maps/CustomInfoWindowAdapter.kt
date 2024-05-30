package com.dicoding.picodiploma.loginwithanimation.view.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    private val view: View = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null)

    override fun getInfoWindow(marker: Marker): View? {
        // Use the default frame
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val story = marker.tag as? ListStoryItem
        story?.let {
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val descTextView = view.findViewById<TextView>(R.id.descTextView)
            titleTextView.text = it.name
            descTextView.text = it.description
        }
        return view
    }
}
