package fi.giao.finnishparliamentapp.repository

import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember

class AppRepository(private val database: AppDatabase) {
    fun getAllMembers() = database.memberDAO.getAllMembers()
    suspend fun insertAllMembers(memberList: List<ParliamentMember>) =
        database.memberDAO.insertAllMembers(memberList)
}