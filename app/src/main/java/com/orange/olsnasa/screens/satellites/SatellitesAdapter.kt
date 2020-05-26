package com.orange.olsnasa.screens.satellites

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.orange.domain.model.SatelliteAbove
import com.orange.olsnasa.R
import com.orange.olsnasa.extensions.loadCropped
import com.orange.olsnasa.utils.EventHandlerWithParam
import com.orange.olsnasa.utils.EventWithParam

class SatellitesAdapter(private val items: List<SatelliteAbove>) :
    RecyclerView.Adapter<SatellitesAdapter.ViewHolder>() {

    private val cardClicked = EventHandlerWithParam<SatelliteAbove>()
    val onCardClicked = EventWithParam(cardClicked)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(vh: ViewHolder, positon: Int) {
        val item = items[positon]

        vh.itemView.setOnClickListener {
            cardClicked(item)
        }
        with(vh) {
            item.satIcon?.let {
                ivIcon.loadCropped(it)
            }
            tvId.text = "ID: ${item.satid}"
            tvName.text = "Name: ${item.satname}"
            tvCoordinates.text = "Coordinates: ${item.satlat} / ${item.satlng}"
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_satellite, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon = itemView.findViewById<ImageView>(R.id.ivSatelliteIcon)
        val tvId = itemView.findViewById<TextView>(R.id.tvSatelliteId)
        val tvName = itemView.findViewById<TextView>(R.id.tvSatelliteName)
        val tvCoordinates = itemView.findViewById<TextView>(R.id.tvSatelliteCoordinates)
    }
}
