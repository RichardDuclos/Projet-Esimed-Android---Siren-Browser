package com.example.sirene.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import com.example.sirene.R
import com.example.sirene.model.SirenDataBase


class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = SirenDataBase.getDatabase(context as Context)
        val settings = database.SettingsDAO().getAll()
        val setting = settings[0]

        val switch = view.findViewById<SwitchCompat>(R.id.filter_settings_switch)
        if(setting.use == true){
            if(!switch.isChecked)
            switch.toggle()
        }

        switch.setOnClickListener{
            setting.use = !setting.use
            database.SettingsDAO().update(setting)
        }
        val saveFilterSettings = view.findViewById<Button>(R.id.save_filter_settings)
        val zipcode = view.findViewById<EditText>(R.id.etZipCode)
        val departement = view.findViewById<EditText>(R.id.etDepartement)
        zipcode.setText(setting.code_postal)
        departement.setText(setting.departement_code)
        saveFilterSettings.setOnClickListener{
            setting.code_postal = zipcode.text.toString()
            setting.departement_code = departement.text.toString()
            database.SettingsDAO().update(setting)
        }

        val btRemoveHistory = view.findViewById<Button>(R.id.bt_clear_history)
        btRemoveHistory.setOnClickListener{
            database.ResearchDAO().clear()
        }
    }

    override fun onResume() {
        super.onResume()
        val database = SirenDataBase.getDatabase(context as Context)
        val settings = database.SettingsDAO().getAll()
        val setting = settings[0]
        val saveFilterSettings = (view as View).findViewById<Button>(R.id.save_filter_settings)
        val zipcode = (view as View).findViewById<EditText>(R.id.etZipCode)
        val departement = (view as View).findViewById<EditText>(R.id.etDepartement)
        zipcode.setText(setting.code_postal)
        departement.setText(setting.departement_code)
    }


}