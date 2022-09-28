package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * This AppViewModel class is scoped to the activity. It means that all fragments reference to
 * the same view model objects throughout the app.
 *
 * This method has been discussed with Peter Hjort in the class on 28/9
 * Reference: "Share data between fragments"
 * https://developer.android.com/topic/libraries/architecture/viewmodel
 */
class AppViewModel(application: Application): AndroidViewModel(application) {

    private val appRepository =  AppRepository(AppDatabase.getInstance(application))

    private val _status = MutableLiveData<String>()
    val status:LiveData<String> = _status

    /**
     * Use Transformations.map to get list of party from list of members as LiveData
     */
    private val memberList:LiveData<List<ParliamentMember>> =  appRepository.getAllMembers()
    val partyList:LiveData<List<String>> = Transformations.map(memberList) {
            list -> ParliamentFunctions.listParty(list)
    }

    init {
        getDataFromNetworkAndSave()
    }

    private fun getDataFromNetworkAndSave() {
        viewModelScope.launch {
            try {
                appRepository.getDataFromNetworkAndSave()
                _status.value = "fetching data successfully"
            } catch (error:IOException) {
                _status.value = error.toString()
            }
        }
    }

    fun getMemberListFromParty(party: String):LiveData<List<ParliamentMember>> =
        appRepository.getMembersFromParty(party)
}

