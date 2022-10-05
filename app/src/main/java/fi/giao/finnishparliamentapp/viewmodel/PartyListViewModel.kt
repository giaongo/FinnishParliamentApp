package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
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

