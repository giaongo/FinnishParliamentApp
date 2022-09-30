package fi.giao.finnishparliamentapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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


