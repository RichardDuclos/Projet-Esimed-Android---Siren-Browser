package com.example.sirene.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sirene.*
import com.example.sirene.model.Results
import com.example.sirene.model.SirenDataBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/*private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"*/

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    /*private var param1: String? = null
    private var param2: String? = null*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // pour bundle
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = SirenDataBase.getDatabase(context as Context)



        val searchButton = view.findViewById<ImageButton>(R.id.bt_search)
        val service = SirenService()
        searchButton.setOnClickListener{
            val query = view.findViewById<EditText>(R.id.searchInput).text.toString()
            val recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerview)
            val nbResults = view.findViewById<TextView>(R.id.tvnbresult)
            val progressbar = view.findViewById<ProgressBar>(R.id.companyprogress)


            database.ResearchDAO().updateArchive()
            val searchResults = database.ResearchDAO().getNonArchived(query)

            val dateConverter = DateConverter()


            if(searchResults.count() > 0){
                val research = searchResults[0]
                val companyList = database.CompanyDAO().getAllFromSearch(research.id_search as Long)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = CompanyAdapter(context as Context, (companyList))
                nbResults.text = String.format(getString(R.string.nb_result), (recyclerView.adapter as CompanyAdapter).itemCount.toString(),
                    if( (recyclerView.adapter as CompanyAdapter).itemCount>1) "s"  else  "")

                return@setOnClickListener

            }

            progressbar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
            nbResults.visibility = View.INVISIBLE




            Thread(
                Runnable {


                    service.query(query, 50, object: Callback<Results>{
                        override fun onResponse(call: Call<Results>, response: Response<Results>) {

                            if(response.code() == 200){


                                val companyList = response.body()?.etablissement as List<Company>
                                var research = Research(null, query, null, null, false, dateConverter.toLong(Date()), (response.body()!!.etablissement as List<Company>).count() )
                                val id = database.ResearchDAO().create(research)
                                research = database.ResearchDAO().getOne(id)!!
                                companyList.forEach {
                                    val company = database.CompanyDAO().getOne(it.id as Long)
                                    if(company == null){

                                        val id = database.CompanyDAO().create(it)
                                        val link = Link(
                                            null,
                                            it.id,
                                            research.id_search as Long
                                        )
                                        database.LinkDAO().create(link)
                                    }
                                    else{
                                        val link = Link(
                                            null,
                                            company.id as Long,
                                            research.id_search as Long
                                        )
                                        database.LinkDAO().create(link)
                                    }
                                }
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                recyclerView.adapter = CompanyAdapter(context as Context, (response.body()?.etablissement as List<Company>))
                                nbResults.text = String.format(getString(R.string.nb_result), (recyclerView.adapter as CompanyAdapter).itemCount.toString(),
                                    if( (recyclerView.adapter as CompanyAdapter).itemCount>1) "s"  else  "")
                            }
                            else if(response.code() == 404){
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                recyclerView.adapter = CompanyAdapter(context as Context, listOf<Company>())
                                nbResults.text = String.format(getString(R.string.nb_result), (recyclerView.adapter as CompanyAdapter).itemCount.toString(),
                                    if( (recyclerView.adapter as CompanyAdapter).itemCount>1) "s"  else  "")
                            }
                            progressbar.visibility = View.INVISIBLE
                            recyclerView.visibility = View.VISIBLE
                            nbResults.visibility = View.VISIBLE

                        }
                        override fun onFailure(call: Call<Results>, t: Throwable) {
                            val builder = AlertDialog.Builder(context)

                            builder.setMessage("Problème lors de l'appel au service web")
                            builder.create().show()
                            progressbar.visibility = View.INVISIBLE
                            recyclerView.visibility = View.VISIBLE
                            nbResults.visibility = View.VISIBLE

                        }


                    })

                }
            ).start()
        }
    }

    /*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment search.
         */

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}