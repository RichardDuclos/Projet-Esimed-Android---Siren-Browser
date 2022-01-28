package com.example.sirene.model

import androidx.room.*
import com.example.sirene.Research

@Dao
interface ResearchDAO {

    @Query("select * from research ORDER BY date desc")
    abstract fun getAll() : List<Research>

    @Query("delete from research")
    abstract fun clear()

    @Query("select * from research where id_search = :id_search")
    abstract fun getOne(id_search: Long) : Research?

    @Query("update research set archive = 1 where archive = 0 AND (date + 86400000) < (strftime('%s', 'now') * 1000)")
    abstract fun updateCache()

    @Query("delete from research where date + 8035200000 <(strftime('%s', 'now') * 1000)")
    abstract fun updateArchive()

    @Query("select * from research where text = :text AND zip_code = :zip_code AND departement_code = :departement_code AND archive = 0 LIMIT 1")
    abstract fun getNonArchived(text: String, zip_code: String, departement_code: String) : List<Research>

    @Insert
    fun create(research: Research): Long

    @Delete
    fun delete(research: Research)

    @Update
    fun update(research: Research)

}