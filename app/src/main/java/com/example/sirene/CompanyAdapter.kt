package com.example.sirene

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CompanyAdapter(val context: Context, val company_list: List<Company>) : RecyclerView.Adapter<CompanyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        return CompanyViewHolder(LayoutInflater.from(context).inflate(R.layout.company_row, parent, false))
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.textViewCompanyName.text = company_list[position].nom_raison_sociale


    }

    override fun getItemCount(): Int {
        return company_list.count()
    }
}