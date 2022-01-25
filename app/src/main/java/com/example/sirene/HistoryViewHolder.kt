package com.example.sirene

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryViewHolder(row: View) : RecyclerView.ViewHolder(row) {
    var textViewQuery = row.findViewById<TextView>(R.id.tvquery)
    var textViewDate = row.findViewById<TextView>(R.id.tvDate)
}