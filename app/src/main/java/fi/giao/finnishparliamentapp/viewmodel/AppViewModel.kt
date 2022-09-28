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

    private val memberList:LiveData<List<ParliamentMember>> =  appRepository.getAllMembers()
    val partyList:LiveData<List<String>> = Transformations.map(memberList) {
            list -> ParliamentFunctions.listParty(list)
    }

    /* For getting list of Parliament Member from party name as a LiveData. This way is recommended in
    * https://developer.android.com/topic/libraries/architecture/livedata#transform_livedata
    * over calling the function to return livedata instance every time the requested party changes*/
    private val requestedParty = MutableLiveData<String>()
    val memberListFromParty: LiveData<List<ParliamentMember>> = Transformations.switchMap(requestedParty){
        party -> appRepository.getMembersFromParty(party)
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

    fun setRequestedParty(party: String) {
        requestedParty.value = party
    }
}

