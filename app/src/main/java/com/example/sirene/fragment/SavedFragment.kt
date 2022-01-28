package com.example.sirene.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sirene.Company
import com.example.sirene.CompanyAdapter
import com.example.sirene.R
import com.example.sirene.model.SirenDataBase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/*private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"*/
/**
 * A simple [Fragment] subclass.
 * Use the [SavedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedFragment : Fragment() {
    /* TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null*/
    var CompanyList : List<Company>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.savedRecycler)
        val database = SirenDataBase.getDatabase(context as Context)
        CompanyList = database.CompanyDAO().getAllSaved()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CompanyAdapter(context as Context, (CompanyList as List<Company>))
        val textViewEmpty = view.findViewById<TextView>(R.id.tv_empty_saved)
        if((recyclerView.adapter as CompanyAdapter).itemCount > 0){
            textViewEmpty.visibility = View.INVISIBLE
        }
        else{
            textViewEmpty.visibility = View.VISIBLE

        }
    }

    override fun onResume() {

        super.onResume()
        val view = view
        val recyclerView = (view as View).findViewById<RecyclerView>(R.id.savedRecycler)

        val database = SirenDataBase.getDatabase(context as Context)
        CompanyList = database.CompanyDAO().getAllSaved()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CompanyAdapter(context as Context, (CompanyList as List<Company>))
        val textViewEmpty = view.findViewById<TextView>(R.id.tv_empty_saved)
        if((recyclerView.adapter as CompanyAdapter).itemCount > 0){
            textViewEmpty.visibility = View.INVISIBLE
        }
        else{
            textViewEmpty.visibility = View.VISIBLE

        }
    }
    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SavedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}