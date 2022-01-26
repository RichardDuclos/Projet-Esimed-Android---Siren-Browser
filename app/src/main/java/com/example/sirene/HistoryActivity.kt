package com.example.sirene

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sirene.model.SirenDataBase

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val database = SirenDataBase.getDatabase(this)

        val research : Research = (intent.getSerializableExtra("research") as Research)
        val databaseCompanyList = database.CompanyDAO().getAllFromSearch(research.id_search as Long)

        val recyclerView = findViewById<RecyclerView>(R.id.history_recycler)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CompanyAdapter(this as Context, databaseCompanyList)
        val nbResults = findViewById<TextView>(R.id.tvnbresult)
        nbResults.text = String.format(getString(R.string.nb_result), (recyclerView.adapter as CompanyAdapter).itemCount.toString(),
            if( (recyclerView.adapter as CompanyAdapter).itemCount>1) "s"  else  "")
    }

}