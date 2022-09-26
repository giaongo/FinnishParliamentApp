package fi.giao.finnishparliamentapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParliamentMember (
    @PrimaryKey
    val hetekaId: Int,
    val seatNumber: Int,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String
)