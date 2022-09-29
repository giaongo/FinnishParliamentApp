package fi.giao.finnishparliamentapp.viewmodel

import fi.giao.finnishparliamentapp.database.ParliamentMember

object ParliamentFunctions {

    fun listParty(memberList: List<ParliamentMember>): List<String> {
        return memberList.map { it.party }.toSet().toList().sorted()
    }

}