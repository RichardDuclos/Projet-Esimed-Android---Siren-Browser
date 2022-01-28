package com.example.sirene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sirene.fragment.*
import com.example.sirene.model.NafDatabase
import com.example.sirene.model.SirenDataBase
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private val searchFragment = SearchFragment()
    private val historyFragment = HistoryFragment()
    private val savedFragment = SavedFragment()
    private val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = SirenDataBase.getDatabase(this)
        database.setup()
        database.ResearchDAO().updateArchive()
        val nafdatabase = NafDatabase.getDatabase(this)

        replaceFragment(searchFragment)
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_search -> replaceFragment(searchFragment)
                R.id.page_history -> replaceFragment(historyFragment)
                R.id.page_saved -> replaceFragment(savedFragment)
                R.id.page_settings -> replaceFragment(settingsFragment)
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }

        }

    }
    private fun replaceFragment(fragment: Fragment) : Boolean{
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
        return true
    }
}