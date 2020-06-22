package fr.durandlyam.gsb.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VisiteDao {

    @Insert
    fun insert(visite: Visite)

    @Update
    fun update(visite: Visite)

    @Query("SELECT * FROM visite_table WHERE id = :key")
    fun get(key: Int) : Visite?

    @Delete
    fun delete(visite: Visite)

    @Query("DELETE FROM visite_table")
    fun clear()

    @Query("SELECT * FROM visite_table ORDER BY id DESC")
    fun getAllVisites() : LiveData<List<Visite>>

    @Query("SELECT * FROM visite_table ORDER BY id DESC LIMIT 1")
    fun getLastVisite() : Visite?
}