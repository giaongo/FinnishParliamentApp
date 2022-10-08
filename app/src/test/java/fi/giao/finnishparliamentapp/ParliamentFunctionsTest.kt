package fi.giao.finnishparliamentapp

import fi.giao.finnishparliamentapp.database.MemberFavorite
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.database.ParliamentMember
import org.junit.Assert.*
import org.junit.Test

class ParliamentFunctionsTest {
    @Test
    fun listParty_duplicatedParty_returnUniqueList() {
        // Create a list
        val list = listOf(
            ParliamentMember(1,2,"Ngo","Giao","PS",false,""),
            ParliamentMember(2,3,"Ngueyn","Hieu","PS",false,""),
            ParliamentMember(3,4,"Pritchepa","Eugine","KESK",false,""),
        )
        // Call function
        val result = ParliamentFunctions.listParty(list)
        // Check the result
        assertEquals(result, listOf("KESK","PS"))
    }
    @Test
    fun listParty_emptyData_returnsNull() {
        // Create a list
        val list = emptyList<ParliamentMember>()
        // Call function
        val result = ParliamentFunctions.listParty(list)
        // Check the result
        assertEquals(result, emptyList<String>())
    }

    @Test
    fun listRating_data_returnsListFloat() {
        // Create a list
        val list = listOf(
            MemberReview(1,2,5.43f,"",23),
            MemberReview(2,4,3.4f,"",34)
        )
        // Call function
        val result = ParliamentFunctions.listRating(list)
        // Check the result
        assertEquals(result,listOf(5.43f,3.4f))
    }
    @Test
    fun listHetekaId_data_returnListInt() {
        // Create a list
        val list = listOf(
            MemberFavorite(1,2,true),
            MemberFavorite(1,3,true)
        )
        // Call function
        val result = ParliamentFunctions.listHetekaId(list)
        // Check result
        assertEquals(result, listOf(2,3))
    }
    @Test
    fun checkValueInList_containedInt_returnTrue() {
        // Create input
        val value = 3
        val list = listOf(2,3,4)
        // Call function
        val result = ParliamentFunctions.checkValueInList(value,list)
        // Check result
        assertEquals(result,true)
    }
    @Test
    fun checkValueInList_nullInt_emptyList_returnFalse() {
        // Create input
        val value = null
        val list = emptyList<Int>()
        // Call function
        val result = ParliamentFunctions.checkValueInList(value,list)
        // Check result
        assertEquals(result,false)
    }

    @Test
    fun calculatePercentage_validInput_returnPercentage() {
        // Create input
        val value = "kd"
        val list = listOf(
                "kd",
                "kd",
                "ps",
                "sd"
            )
        // Call function
        val result = ParliamentFunctions.calculatePercentage(value,list)
        // Check result
        assertEquals(result,50.0,0.0)
    }

    @Test
    fun calculatePercentage_emptyParty_emptyList_returnZero() {
        val value = ""
        val list = emptyList<String>()
        val result = ParliamentFunctions.calculatePercentage(value,list)
        assertEquals(result,0.0, 0.0)
    }
}