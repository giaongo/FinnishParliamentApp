package fi.giao.finnishparliamentapp.viewmodel

import fi.giao.finnishparliamentapp.database.ParliamentMember

object ParliamentFunctions {

    fun listParty(memberList: List<ParliamentMember>): List<String> {
        return memberList.map { it.party }.toSet().toList().sorted()
    }

    fun listMemberFromParty(
        memberList: List<ParliamentMember>,
        requestedParty: String
    ): List<ParliamentMember> {
        return memberList.filter { it.party == requestedParty }
            .sortedBy { it.lastname + it.firstname }
    }

}