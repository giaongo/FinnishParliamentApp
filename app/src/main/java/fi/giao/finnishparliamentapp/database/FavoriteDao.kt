package fi.giao.finnishparliamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT *  FROM MemberFavorite")
    fun getAllFavorite() : LiveData<List<MemberFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markFavorite(favoriteMember: MemberFavorite)

    @Query("DELETE FROM MemberFavorite WHERE hetekaId = :memberHetekaId")
    suspend fun unMarkFavorite(memberHetekaId: Int)
}