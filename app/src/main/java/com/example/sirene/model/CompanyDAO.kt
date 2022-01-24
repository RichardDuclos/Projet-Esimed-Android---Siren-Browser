package com.example.sirene.model

import androidx.room.*
import com.example.sirene.Company

@Dao
interface CompanyDAO {

    @Query("select * from company where id = :id")
    abstract fun getOne(id: Long) :  List<Company>

    @Insert
     fun create(company: Company)

    @Delete
     fun delete(company: Company)

    @Update
    fun update(company: Company)

}