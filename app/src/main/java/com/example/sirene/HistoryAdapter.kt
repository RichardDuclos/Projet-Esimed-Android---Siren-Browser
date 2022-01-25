package com.example.sirene

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(val context: Context, val historyList: List<Research>) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_row, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.textViewQuery.text = historyList[position].text
        val dateLong = historyList[position].date
        val dateConverter = DateConverter()
        val date = dateConverter.toDate(dateLong)
        val pattern = "DD/MM/YYYY"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateformat = simpleDateFormat.format(date as Date)
        holder.textViewDate.text = dateformat


    }

    override fun getItemCount(): Int {
        return historyList.count()
    }
}