package fi.giao.finnishparliamentapp.database

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.parcelize.Parcelize


@Parcelize
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
): Parcelable

@Dao
interface MemberDao {
    @Query("SELECT * FROM ParliamentMember")
    fun getAllMembers(): LiveData<List<ParliamentMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMembers(memberList: List<ParliamentMember>)

    @Query("SELECT * FROM ParliamentMember WHERE party = :requestedParty ORDER BY lastname ASC")
    fun getMembersFromParty(requestedParty:String): LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM ParliamentMember WHERE hetekaId IN(:listHetekaId)")
    fun getFavoriteParliamentMember(listHetekaId:List<Int>): LiveData<List<ParliamentMember>>
}