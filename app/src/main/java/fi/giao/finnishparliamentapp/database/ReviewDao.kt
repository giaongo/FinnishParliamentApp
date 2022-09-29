package fi.giao.finnishparliamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReviewDao {
    @Query("SELECT * FROM MemberReview WHERE hetekaId = :hetekaId")
    fun getAllReviewsByHetekaId(hetekaId: Int): LiveData<List<MemberReview>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(memberReview: MemberReview)

    @Update
    suspend fun updateReview(memberReview: MemberReview)

    @Delete
    suspend fun deleteReview(memberReview: MemberReview)
}