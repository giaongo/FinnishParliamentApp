package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.IllegalArgumentException

/**
 * This is ViewModel class is for PartyListFragment
 */
class PartyListViewModel(application: Application): AndroidViewModel(application) {

    private val appRepository = AppRepository(AppDatabase.getInstance(application))

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    /**
     * Use Transformations.map to get list of party from list of members as LiveData
     */
    private val memberList: LiveData<List<ParliamentMember>> =  appRepository.getAllMembers()
    val partyList: LiveData<List<String>> = Transformations.map(memberList) { list ->
        ParliamentFunctions.listParty(list)
    }

    init {
        getDataFromNetworkAndSave()
    }

    private fun getDataFromNetworkAndSave() {
        viewModelScope.launch {
            try {
                appRepository.getDataFromNetworkAndSave()
                _status.value = "fetching data successfully"
            } catch (error: IOException) {
                _status.value = error.toString()
            }
        }
    }
}


/**
 * Factory for constructing viewModel with parameter
 */
class PartyListViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(PartyListViewModel::class.java))  {
            PartyListViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}

