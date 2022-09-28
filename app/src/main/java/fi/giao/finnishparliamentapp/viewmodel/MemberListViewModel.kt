package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import java.lang.IllegalArgumentException

/**
 * This ViewModel class is for MemberListFragment
 */
class MemberListViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val requestedParty = MutableLiveData<String>()

    /**
     * This method is more recommended in
     * https://developer.android.com/topic/libraries/architecture/livedata#transform_livedata
     * as it avoids the returned new LiveData object each time
     * appRepository.getMembersFromParty(newParty) is called
     * The memberListFromParty property will be updated only when the requestedParty is changed
     */
    val memberListFromParty: LiveData<List<ParliamentMember>> = Transformations.switchMap(requestedParty) {
        newParty -> appRepository.getMembersFromParty(newParty)
    }

    fun setParty(party:String) {
        requestedParty.value = party
    }


}

/**
 * Factory for constructing viewModel with parameter
 */
class MemberListViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MemberListViewModel::class.java))  {
            MemberListViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}