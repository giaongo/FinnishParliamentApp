package fi.giao.finnishparliamentapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemberFavorite (
        @PrimaryKey(autoGenerate = true)
        val id:Int,
        val hetekaId:Int,
        val isFavorite: Boolean
        )