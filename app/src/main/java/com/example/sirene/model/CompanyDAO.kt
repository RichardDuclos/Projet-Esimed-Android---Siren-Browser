package com.example.sirene.model

import androidx.room.*
import com.example.sirene.Company

@Dao
interface CompanyDAO {


    @Query("select * from company")
    abstract fun getAll() : List<Company>

    @Query("select * from company where id = :id")
    abstract fun getOne(id: Long) :  Company?

    @Query("select * from company join link on link.id_company = company.id join research using (id_search) where research.id_search = :id_search")
    abstract fun getAllFromSearch(id_search: Long): List<Company>

    @Insert
     fun create(company: Company): Long

    @Delete
     fun delete(company: Company)

    @Update
    fun update(company: Company)

}