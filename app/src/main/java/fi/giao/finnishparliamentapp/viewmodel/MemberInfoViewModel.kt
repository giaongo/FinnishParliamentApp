package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.MemberFavorite
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * This view model will be referenced by MemberInfoFragment. It shows member info,
 * calculates average rating and shows user reviews. It also allows user to mark or un-mark
 * member as favorite.
 */
class MemberInfoViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val hetekaId = MutableLiveData<Int>()

    /* get list of MemberReview as a LiveData based on the change of hetekaId and changing in value
     of appRepository.getAllReviewsByHetekaId(id) */
    val allReviewsByHetekaId: LiveData<List<MemberReview>> = Transformations.switchMap(hetekaId) {
        id -> appRepository.getAllReviewsByHetekaId(id)
    }

    /*
     Get averageRating as a LiveData depending on the change of allReviewsByHetekaId LiveData
     */
    val averageRating: LiveData<Float> = Transformations.map(allReviewsByHetekaId) {
                val listRating = ParliamentFunctions.listRating(it)
                listRating.average().toFloat()
    }

    /* Get isMarkedFavorite as a LiveData observed from favoriteList in which it checks whether
    current member is in the favorite list or not. If yes, the value of isMarkedFavorite is true
     */
    private val favoriteList: LiveData<List<MemberFavorite>> = appRepository.getAllFavorite()
    val isMarkedFavorite: LiveData<Boolean> = Transformations.map(favoriteList) {
            val hetekaIdList = ParliamentFunctions.listHetekaId(it)
            ParliamentFunctions.checkValueInList(hetekaId.value,hetekaIdList)
    }

    fun setHetekaId(id:Int) {
        hetekaId.value = id
    }

    fun markFavorite(favoriteMember:MemberFavorite) = viewModelScope.launch {
        appRepository.markFavorite(favoriteMember)
    }

    fun unMarkFavorite(hetekaId:Int) = viewModelScope.launch {
        appRepository.unMarkFavorite(hetekaId)
    }

}

class MemberInfoViewModelFactory(val app:Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MemberInfoViewModel::class.java)) {
            MemberInfoViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}

