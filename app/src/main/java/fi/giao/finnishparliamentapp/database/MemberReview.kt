package fi.giao.finnishparliamentapp.database

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MemberReview (
    @PrimaryKey(autoGenerate = true)
    val reviewId: Int,
    val hetekaId: Int,
    val rating: Float,
    val comment: String,
    val timeStamp: Long
): Parcelable

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
