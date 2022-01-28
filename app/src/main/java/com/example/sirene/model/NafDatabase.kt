package com.example.sirene.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sirene.*

@Database(version = 2, entities = [code_naf::class])
abstract class NafDatabase : RoomDatabase(){

    abstract fun NAFDAO() : NAFDAO
    companion object{
        var INSTANCE: NafDatabase? = null
        fun getDatabase(context: Context): NafDatabase
        {
            if(INSTANCE ==null){
                INSTANCE = Room
                    .databaseBuilder(context, NafDatabase::class.java, "naf.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .createFromAsset("databases/naf.db")
                    .build()
            }
            return INSTANCE!!
        }
    }

}