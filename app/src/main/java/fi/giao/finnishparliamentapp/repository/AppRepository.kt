package fi.giao.finnishparliamentapp.repository

import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.network.MemberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val database: AppDatabase) {
    fun getAllMembers() = database.memberDAO.getAllMembers()

    /* Function that retrieves data from network and save to local database.
    * This function uses IO dispatcher to make sure it is safe to call from main thread*/
    suspend fun getDataFromNetworkAndSave() {
        withContext(Dispatchers.IO) {
            val memberList = MemberApi.retrofitService.getMembers()
            database.memberDAO.insertAllMembers(memberList)
        }
    }
}