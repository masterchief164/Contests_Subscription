package com.example.contestssubscription.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SiteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(site: Site)
    @Update
    suspend fun update(site: Site)
    @Query("SELECT * from sites WHERE id = :id")
    fun getSite(id: Int): Flow<Site>
    @Query("Select * from sites Order By `Site Name` ASC")
    fun getSites():Flow<ArrayList<Site>>

}