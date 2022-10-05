package fi.giao.finnishparliamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fi.giao.finnishparliamentapp.database.ParliamentMember

@Dao
interface MemberDao {
    @Query("SELECT * FROM ParliamentMember")
    fun getAllMembers(): LiveData<List<ParliamentMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMembers(memberList: List<ParliamentMember>)

    @Query("SELECT * FROM ParliamentMember WHERE party = :requestedParty ORDER BY lastname ASC")
    fun getMembersFromParty(requestedParty:String): LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM ParliamentMember WHERE hetekaId IN(:listHetekaId)")
    suspend fun getFavoriteParliamentMember(listHetekaId:List<Int>):List<ParliamentMember>
}