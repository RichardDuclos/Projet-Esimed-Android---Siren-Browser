package com.example.sirene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sirene.fragment.*
import com.example.sirene.model.SirenDataBase
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private val searchFragment = SearchFragment()
    private val historyFragment = HistoryFragment()
    private val savedFragment = SavedFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = SirenDataBase.getDatabase(this)
        database.seed()


        replaceFragment(searchFragment)
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_search -> replaceFragment(searchFragment)
                R.id.page_history -> replaceFragment(historyFragment)
                R.id.page_saved -> replaceFragment(savedFragment)
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