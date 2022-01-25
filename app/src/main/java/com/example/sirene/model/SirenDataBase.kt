package com.example.sirene.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sirene.Company
import com.example.sirene.Link
import com.example.sirene.Research

@Database(version = 9, entities = [Company::class, Research::class, Link::class])
abstract class SirenDataBase : RoomDatabase(){
    abstract fun CompanyDAO() : CompanyDAO
    abstract fun ResearchDAO() : ResearchDAO
    abstract fun LinkDAO() : LinkDAO
    companion object{
        var INSTANCE: SirenDataBase? = null
        fun getDatabase(context: Context): SirenDataBase
        {
            if(INSTANCE ==null){
                INSTANCE = Room
                    .databaseBuilder(context, SirenDataBase::class.java, "siren.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
    fun seed(){
        if(CompanyDAO().getAll().count() == 0){
            val company = Company(1, "e", "e", "e", "e"
                , "e", "e", "e", "e",
                "e" )
            CompanyDAO().create(company)
            val search = Research(null, "eeeeeeeedfssteghysethee", null , null, false, 980347831000, 30  )
            ResearchDAO().create(search)
        }

    }
}