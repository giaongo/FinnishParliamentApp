package fi.giao.finnishparliamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemberDAO {
    @Query("SELECT * FROM ParliamentMember")
    fun getAllMembers(): LiveData<List<ParliamentMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMembers(memberList: List<ParliamentMember>)
}