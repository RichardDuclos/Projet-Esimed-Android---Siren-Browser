package com.example.sirene.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sirene.*
import com.example.sirene.model.Results
import com.example.sirene.model.SirenDataBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*
import android.text.Editable

import android.text.TextWatcher
import android.view.KeyEvent
import com.example.sirene.model.NafDatabase

import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.view.inputmethod.EditorInfo

import android.widget.TextView

import android.widget.TextView.OnEditorActionListener








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
    private var query: String = ""
    private var zipCodeValue: String = ""
    private var departementCodeValue: String = ""
    private var companyList : List<Company> = listOf<Company>()
    //private var nafValue : String = ""
    //private var first = false;
    //private var naf_list = listOf<String>()
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
        //val nafdatabase = NafDatabase.getDatabase(context as Context)

        if(savedInstanceState != null)
        {
            if(savedInstanceState.containsKey("query")){
                query = savedInstanceState.getString("query") as String
                view.findViewById<EditText>(R.id.searchInput).setText(query)
            }
            if(savedInstanceState.containsKey("zipCode")){
                zipCodeValue = savedInstanceState.getString("zipCode") as String
                view.findViewById<EditText>(R.id.etZipCode).setText(zipCodeValue)
            }
            if(savedInstanceState.containsKey("departementCode")){
                departementCodeValue = savedInstanceState.getString("departementCode") as String
                view.findViewById<EditText>(R.id.etDepartement).setText(departementCodeValue)
            }
            if(savedInstanceState.containsKey("companyList")){
                companyList = savedInstanceState.getSerializable("companyList") as List<Company>

            }
            /*if(savedInstanceState.containsKey("nafValue")){
                nafValue = savedInstanceState.getString("nafValue") as String
            }*/

        }

        val settings = database.SettingsDAO().getAll()
        val setting = settings[0]

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CompanyAdapter(context as Context, companyList)

        val departementCode = view.findViewById<EditText>(R.id.etDepartement)
        val zipCode = view.findViewById<EditText>(R.id.etZipCode)


        /*val etNaf = view.findViewById<EditText>(R.id.etNaf)
        val spinner = view.findViewById<Spinner>(R.id.spinnerNaf)*/

        if(setting.use){
            if(setting.code_postal != "" && zipCode.text.toString() != ""){
                zipCode.setText(setting.code_postal)
            }
            if(setting.departement_code != "" && departementCode.text.toString() != ""){
                departementCode.setText(setting.departement_code)
            }
        }


        val searchButton = view.findViewById<ImageButton>(R.id.bt_search)



        val nbResults = view.findViewById<TextView>(R.id.tvnbresult)
        if(companyList.count()>0){
            nbResults.text = String.format(getString(R.string.nb_result), (recyclerView.adapter as CompanyAdapter).itemCount.toString(),
                if( (recyclerView.adapter as CompanyAdapter).itemCount>1) "s"  else  "")
        }


        val service = SirenService()
        searchButton.setOnClickListener{
            (activity as Activity).hideKeyboard()
            query = view.findViewById<EditText>(R.id.searchInput).text.toString()

            val departementCodeValue = departementCode.text.toString()
            val zipCodeValue = zipCode.text.toString()
            setting.code_postal = zipCodeValue
            setting.departement_code = departementCodeValue
            database.SettingsDAO().update(setting)
            if(query == ""){
                Toast.makeText(context, getString(R.string.empty_search), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val progressbar = view.findViewById<ProgressBar>(R.id.companyprogress)

            database.ResearchDAO().updateCache()
            val searchResults = database.ResearchDAO().getNonArchived(query, zipCodeValue, departementCodeValue)

            val dateConverter = DateConverter()


            if(searchResults.count() > 0){
                val research = searchResults[0]
                companyList = database.CompanyDAO().getAllFromSearch(research.id_search as Long)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = CompanyAdapter(context as Context, companyList)
                nbResults.text = String.format(getString(R.string.nb_result), (recyclerView.adapter as CompanyAdapter).itemCount.toString(),
                    if( (recyclerView.adapter as CompanyAdapter).itemCount>1) "s"  else  "")

                return@setOnClickListener

            }

            progressbar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
            nbResults.visibility = View.INVISIBLE




            Thread(
                Runnable {


                    service.query(query, 50, zipCodeValue, departementCodeValue, object: Callback<Results>{
                        override fun onResponse(call: Call<Results>, response: Response<Results>) {

                            if(response.code() == 200){


                                val companyListAPI = response.body()?.etablissement as List<Company>
                                var research = Research(null, query, zipCodeValue, departementCodeValue, false, dateConverter.toLong(Date()), companyListAPI.count() )
                                val id = database.ResearchDAO().create(research)
                                research = database.ResearchDAO().getOne(id)!!
                                companyListAPI.forEach {
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
                                        database.CompanyDAO().update(company)
                                    }
                                }
                                companyList = database.CompanyDAO().getAllFromSearch(research.id_search as Long)
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                recyclerView.adapter = CompanyAdapter(context as Context, companyList)
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
                            if(context != null){
                                val builder = AlertDialog.Builder(context)

                                builder.setMessage("Problème lors de l'appel au service web")
                                builder.create().show()
                            }

                            progressbar.visibility = View.INVISIBLE
                            recyclerView.visibility = View.VISIBLE
                            nbResults.visibility = View.VISIBLE

                        }


                    })

                }
            ).start()
        }

        /*updateSpinnerWithNaf(spinner, etNaf.text.toString())
        etNaf.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                updateSpinnerWithNaf(spinner, etNaf.text.toString())
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {}
        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(!first){
                        first = !first
                    }
                    else{
                println("item selected")
                        setEditTextFromSpinner(spinner, etNaf, position)

                    }



            }

        }*/

    }
    /*fun setEditTextFromSpinner(spinner: Spinner, editText: EditText, position: Int){
        val codeDAO = NafDatabase.getDatabase(context as Context).NAFDAO()

        val naf = codeDAO.getWithFilterAndPosition(editText.text.toString(), position)
        if(naf.description != editText.text.toString()){
            editText.setText(naf.description)

        }
    }*/
    /*fun updateSpinnerWithNaf(spinner : Spinner, codeinput : String) {
        val codeDAO = NafDatabase.getDatabase(context as Context).NAFDAO()
        val filtre = codeinput
        val naf_list = codeDAO.getAllFilter(filtre)
        val adapter = ArrayAdapter(context as Context, android.R.layout.simple_spinner_item, naf_list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = adapter

    }*/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("query", query )
        outState.putString("zipCode", zipCodeValue )
        outState.putString("departementCode", departementCodeValue )
        outState.putSerializable("companyList", companyList as Serializable)
        //outState.putString("nafValue", nafValue )

    }
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    override fun onResume() {
        super.onResume()
        val database = SirenDataBase.getDatabase(context as Context)
        val settings = database.SettingsDAO().getAll()
        val setting = settings[0]



        val departementCode = (view as View).findViewById<EditText>(R.id.etDepartement)
        val zipCode = (view as View).findViewById<EditText>(R.id.etZipCode)
        if(setting.use){
            zipCode.setText(setting.code_postal)
            departementCode.setText(setting.departement_code)
        }

    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
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