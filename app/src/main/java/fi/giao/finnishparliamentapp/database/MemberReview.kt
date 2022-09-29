package fi.giao.finnishparliamentapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemberReview (
    @PrimaryKey(autoGenerate = true)
    val reviewId: Int,
    val hetekaId: Int,
    val rating: Float,
    val comment: String,
    val timeStamp: Long
)


