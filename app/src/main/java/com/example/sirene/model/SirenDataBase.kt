package com.example.sirene.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sirene.Company

@Database(version = 1, entities = [Company::class])
abstract class SirenDataBase : RoomDatabase(){
    abstract fun CompanyDAO() : CompanyDAO
    companion object{
        var INSTANCE: SirenDataBase? = null
        fun getDatabase(context: Context): SirenDataBase
        {
            if(INSTANCE ==null){
                INSTANCE = Room
                    .databaseBuilder(context, SirenDataBase::class.java, "siren.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
    fun seed(){
        val company = Company(1, "e", "e", "e", "e"
            , "e", "e", "e", "e"
            )
        CompanyDAO().create(company)
    }
}