package com.example.sirene

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CompanyAdapter(val context: Context, val company_list: List<Company>) : RecyclerView.Adapter<CompanyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        return CompanyViewHolder(LayoutInflater.from(context).inflate(R.layout.company_row, parent, false))
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = company_list[position]

        holder.textViewCompanyName.text = company.nom_raison_sociale
        var address = ""
        if(company.numero_voie != null){
            address += company.numero_voie + " "
        }
        if(company.indice_repetition != null){
            address += company.indice_repetition + " "
        }
        if(company.type_voie != null){
            address += company.type_voie + " "
        }
        if(company.libelle_voie != null){
            address += company.libelle_voie + " "
        }
        if(company.code_postal != null){
            address += company.code_postal + " "
        }
        if(company.libelle_commune != null){
            address += company.libelle_commune + " "
        }
        if(company.cedex != null){
            address += "cedex " + company.cedex + " "
        }

        holder.textViewAdress.text =   address
        holder.itemView.setOnClickListener{
            val intent = Intent(context, CompanyDetailsActivity::class.java).apply {
                putExtra("company", company_list[position])
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return company_list.count()
    }
}