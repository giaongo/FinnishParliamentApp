package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.ParliamentFunctions
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.*

/**
 * Date: 5/10/2022
 * Name:Giao Ngo
 * Student id: 2112622
 * This view model does mapping 2 tables with hetekaId and return the list of favorite members that
 * contain their detail info (such as name and party).
 */
class FavoriteListViewModel(application: Application):AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))

    /*
     Get list of hetekaId based on list of favorite data retrieved from MemberFavorite table
     Based on that list of hetekaId, retrieve equivalent list of members from ParliamentMember table.
     This method is to map 2 tables with hetekaId column programmatically.
     The favoriteMemberList from ParliamentMember table changes whenever favorite data retrieved
     from MemberFavorite table changes. And if the member detail of that favorite member in
     ParliamentMember changes, favoriteMemberList observes and updates to UI accordingly.
     */
    val favoriteMemberList: LiveData<List<ParliamentMember>> = Transformations
        .switchMap(appRepository.getAllFavorite()) {
           val listHetekaId =  ParliamentFunctions.listHetekaId(it)
            appRepository.getFavoriteParliamentMember(listHetekaId)
    }

    fun unMarkFavorite(hetekaId:Int) = viewModelScope.launch {
        appRepository.unMarkFavorite(hetekaId)
    }
}

class FavoriteListViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavoriteListViewModel::class.java)) {
            FavoriteListViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}