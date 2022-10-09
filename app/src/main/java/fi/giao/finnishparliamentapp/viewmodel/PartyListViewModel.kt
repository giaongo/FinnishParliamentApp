package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.github.mikephil.charting.data.PieEntry
import fi.giao.finnishparliamentapp.ParliamentFunctions
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import java.lang.IllegalArgumentException

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This view model is for PartyListFragment and obtains the list of identical party names
 * by using Transformations.map
 */
class PartyListViewModel(application: Application): AndroidViewModel(application) {

    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val memberList: LiveData<List<ParliamentMember>> = appRepository.getAllMembers()
     // Use Transformations.map to get list of party from list of members as LiveData
    val partyList: LiveData<List<String>> = Transformations.map(memberList) { list ->
        ParliamentFunctions.listParty(list)
    }
    private val _entries: MutableList<PieEntry> = mutableListOf()
    val entries: List<PieEntry> = _entries
    private val duplicatedParty:LiveData<List<String>> = Transformations.map(memberList) { list ->
        list.map { it.party }
    }

    val partyPercentage:LiveData<List<Pair<String,Double>>> = Transformations.map(duplicatedParty) {
        calculatePartyPercentage(it)
    }

    private fun calculatePartyPercentage(duplicatedListParty:List<String>): List<Pair<String,Double>> {
        val percentageList = mutableListOf<Pair<String,Double>>()
        val uniquePartyList = duplicatedListParty.toSet().toList()
        if (uniquePartyList.isNotEmpty()) {
            uniquePartyList.forEach { party ->
                val percentage = ParliamentFunctions.calculatePercentage(party,duplicatedListParty)
                percentageList.add(Pair(party,percentage))
                _entries.add(PieEntry(percentage.toFloat(),party))
            }
        }
        return percentageList.toList()
    }
}

class PartyListViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(PartyListViewModel::class.java))  {
            PartyListViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}

