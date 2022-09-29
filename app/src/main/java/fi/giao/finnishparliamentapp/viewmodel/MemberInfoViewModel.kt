package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * This view model will be referenced by 2 fragments (MemberInfoFragment and AddReviewFragment)
 * This view model once created will be scoped to MainActivity.
 *
 * This method of sharing view model has been discussed with teacher Peter in class and got
 * approval for usage for this application
 */
class MemberInfoViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val hetekaId = MutableLiveData<Int>()

    val allReviewsByHetekaId: LiveData<List<MemberReview>> = Transformations.switchMap(hetekaId) {
        id -> appRepository.getAllReviewsByHetekaId(id)
    }
    fun setHetekaId(id:Int) {
        hetekaId.value = id
    }

    fun insertReview(review:MemberReview) = viewModelScope.launch {
        appRepository.insertReview(review)
    }
    fun updateReview(review: MemberReview) = viewModelScope.launch {
        appRepository.updateReview(review)
    }
    fun deleteReview(review: MemberReview) = viewModelScope.launch {
        appRepository.deleteReview(review)
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

