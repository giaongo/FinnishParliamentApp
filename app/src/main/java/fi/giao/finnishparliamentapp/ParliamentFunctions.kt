package fi.giao.finnishparliamentapp

import fi.giao.finnishparliamentapp.database.MemberFavorite
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.database.ParliamentMember
import kotlin.math.roundToInt

/**
 * Date: 5/10/2022
 * Name:Giao Ngo
 * Student id: 2112622
 * This singleton object defines basic functions that are called within the app, mostly in view
 * models
 */
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

    // This function is to calculate percentage of party in relation to total number of party
    fun calculatePercentage(party: String, listParty:List<String>): Double {
        if (party.isNotEmpty() && listParty.isNotEmpty()) {
            if (listParty.contains(party)){
                val partyCount  = listParty.count { it == party }
                val result = partyCount.toDouble().div(listParty.size).times(100)
                return String.format("%.1f",result).toDouble()
            }
        }
        return 0.0
    }
}