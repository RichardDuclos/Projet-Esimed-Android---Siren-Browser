package com.example.sirene.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sirene.*

@Database(version = 21, entities = [Company::class, Research::class, Link::class, Save::class, Settings::class])
abstract class SirenDataBase : RoomDatabase(){
    abstract fun CompanyDAO() : CompanyDAO
    abstract fun ResearchDAO() : ResearchDAO
    abstract fun LinkDAO() : LinkDAO
    abstract fun SaveDAO() : SaveDAO
    abstract fun SettingsDAO() : SettingsDAO
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
    fun setup(){
        if(SettingsDAO().getAll().count() == 0){
            val setting = Settings(null, "" , "", "", true)
            SettingsDAO().create(setting)
        }

    }
}