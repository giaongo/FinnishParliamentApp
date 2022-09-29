package fi.giao.finnishparliamentapp.repository

import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.network.MemberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val database: AppDatabase) {
    fun getAllMembers() = database.memberDao.getAllMembers()

    /* Function that retrieves data from network and save to local database.
    * This function uses IO dispatcher to make sure it is safe to call from main thread*/
    suspend fun getDataFromNetworkAndSave() {
        withContext(Dispatchers.IO) {
            val memberList = MemberApi.retrofitService.getMembers()
            database.memberDao.insertAllMembers(memberList)
        }
    }

    fun getMembersFromParty(party:String) = database.memberDao.getMembersFromParty(party)
    fun getAllReviewsByHetekaId(hetekaId:Int) = database.reviewDao.getAllReviewsByHetekaId(hetekaId)
    suspend fun insertReview(review:MemberReview) = database.reviewDao.insertReview(review)
    suspend fun updateReview(review:MemberReview) = database.reviewDao.updateReview(review)
    suspend fun deleteReview(review:MemberReview) = database.reviewDao.deleteReview(review)

}