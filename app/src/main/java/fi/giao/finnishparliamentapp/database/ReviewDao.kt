package fi.giao.finnishparliamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.DELETE

@Dao
interface ReviewDao {
    @Query("SELECT * FROM MemberReview WHERE hetekaId = :hetekaId")
    fun getAllReviewsByHetekaId(hetekaId: Int): LiveData<List<MemberReview>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(memberReview: MemberReview)

    @Update
    suspend fun updateReview(memberReview: MemberReview)

    @DELETE
    suspend fun deleteReview(memberReview: MemberReview)
}