package com.orange.olsnasa.screens.scores

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.orange.domain.model.Score
import com.orange.olsnasa.R

class ScoresAdapter(private val items: List<Score>) :
    RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

    override fun onBindViewHolder(vh: ViewHolder, positon: Int) {
        val item = items[positon]
        with(vh) {
            tvScoreName.text = item.name
            tvScoreValue.text = item.score.toString()
            tvScoreId.text = (positon + 1).toString()
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_score, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivScoreIcon = itemView.findViewById<ImageView>(R.id.ivScoreIcon)
        val tvScoreId = itemView.findViewById<TextView>(R.id.tvScoreId)
        val tvScoreName = itemView.findViewById<TextView>(R.id.tvScoreName)
        val tvScoreValue = itemView.findViewById<TextView>(R.id.tvScoreValue)
    }
}
