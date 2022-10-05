package fi.giao.finnishparliamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This contains MemberFavorite entity and queries that applied for this entity in FavoriteDao
 */
@Entity
data class MemberFavorite (
        @PrimaryKey(autoGenerate = true)
        val id:Int,
        val hetekaId:Int,
        val isFavorite: Boolean
        )


@Dao
interface FavoriteDao {
        @Query("SELECT *  FROM MemberFavorite")
        fun getAllFavorite() : LiveData<List<MemberFavorite>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun markFavorite(favoriteMember: MemberFavorite)

        @Query("DELETE FROM MemberFavorite WHERE hetekaId = :memberHetekaId")
        suspend fun unMarkFavorite(memberHetekaId: Int)
}