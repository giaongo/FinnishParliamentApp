package fi.giao.finnishparliamentapp.viewmodel

import fi.giao.finnishparliamentapp.database.MemberFavorite
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.database.ParliamentMember

object ParliamentFunctions {

    fun listParty(memberList: List<ParliamentMember>): List<String> {
        return memberList.map { it.party }.toSet().toList().sorted()
    }
    fun listRating(reviewList: List<MemberReview>):List<Float> {
        return reviewList.map { it.rating }
    }

    fun listHetekaId(favoriteList: List<MemberFavorite>): List<Int> {
        return favoriteList.map { it.hetekaId }
    }

    fun checkValueInList(input: Int?, list:List<Int>):Boolean {
        if (input != null) {
            return list.contains(input)
        }
        return false
    }
}