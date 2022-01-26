package com.example.sirene

import android.content.Context
import android.content.Intent
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
        var string = ""
        val zipcode : String? = historyList[position].zip_code
        val departement_code : String? = historyList[position].departement_code
        if(zipcode!= "" && departement_code != "")
        {
            string = "$departement_code - $zipcode "
        }
        else{
            if(zipcode != ""){
                string = "$zipcode "
            }
            else if(departement_code != ""){
                string = "$departement_code "
            }

        }
        string += dateformat
        holder.textViewDate.text = string
        holder.itemView.setOnClickListener{
            val intent = Intent(context, HistoryActivity::class.java).apply {
                putExtra("research", historyList[position])
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return historyList.count()
    }
}