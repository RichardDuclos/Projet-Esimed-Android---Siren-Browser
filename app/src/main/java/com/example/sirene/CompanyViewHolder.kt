package com.example.sirene

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompanyViewHolder(row: View) : RecyclerView.ViewHolder(row) {
    var textViewCompanyName = row.findViewById<TextView>(R.id.companyname)
}