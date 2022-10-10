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
 * This view model class is for MemberListFragment. Its task is to call functions from repository to
 * return a list of members by party name based on the value change of party set by fragment.
 */
class MemberListViewModel(application: Application) : AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val requestedParty = MutableLiveData<String>()

    /*
     The memberListFromParty is an immutable LiveData that will be updated with the result from
     calling the scoped function whenever requestedParty value changes. As the returned result from
     map is LiveData the value of memberListFromParty is observed accordingly.
     - Learning source:
        https://developer.android.com/topic/libraries/architecture/livedata#transform_livedata
        https://stackoverflow.com/questions/47610676/how-and-where-to-use-transformations-switchmap
     */
    val memberListFromParty: LiveData<List<ParliamentMember>> =
        Transformations.switchMap(requestedParty) {
            appRepository.getMembersFromParty(it)
        }

    fun setParty(party: String) {
        requestedParty.value = party
    }

}

class MemberListViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MemberListViewModel::class.java)) {
            MemberListViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}