package fi.giao.finnishparliamentapp.repository

import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.MemberFavorite
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.network.MemberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This repository contains methods to fetch data from Remote Data Source and from Room database
 */
class AppRepository(private val database: AppDatabase) {

    /* Function that retrieves data from network and save to local database.
    * This function uses IO dispatcher to make sure it is safe to call from main thread*/
    suspend fun getDataFromNetworkAndSave() {
        withContext(Dispatchers.IO) {
            val memberList = MemberApi.retrofitService.getMembers()
            database.memberDao.insertAllMembers(memberList)
        }
    }

    // These below functions are from memberDao
    fun getAllMembers() = database.memberDao.getAllMembers()
    fun getMembersFromParty(party: String) = database.memberDao.getMembersFromParty(party)
    fun getFavoriteParliamentMember(listHeteka: List<Int>) = database.memberDao
        .getFavoriteParliamentMember(listHeteka)

    // These below functions are from reviewDao
    fun getAllReviewsByHetekaId(hetekaId: Int) =
        database.reviewDao.getAllReviewsByHetekaId(hetekaId)

    suspend fun insertReview(review: MemberReview) = database.reviewDao.insertReview(review)
    suspend fun updateReview(review: MemberReview) = database.reviewDao.updateReview(review)
    suspend fun deleteReview(review: MemberReview) = database.reviewDao.deleteReview(review)

    // These below functions are from favoriteDao
    fun getAllFavorite() = database.favoriteDao.getAllFavorite()
    suspend fun markFavorite(favoriteMember: MemberFavorite) = database.favoriteDao
        .markFavorite(favoriteMember)

    suspend fun unMarkFavorite(hetekaId: Int) = database.favoriteDao
        .unMarkFavorite(hetekaId)

}