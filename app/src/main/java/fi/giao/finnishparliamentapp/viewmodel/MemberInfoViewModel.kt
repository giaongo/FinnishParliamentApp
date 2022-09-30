package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * This view model will be referenced by 3 fragments (MemberInfoFragment, AddReviewFragment and UpdateReviewFragment)
 * This view model once created will be scoped to MainActivity.
 *
 * This method of sharing view model has been discussed with teacher Peter in class and got
 * approval for usage for this application
 */
class MemberInfoViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val hetekaId = MutableLiveData<Int>()

    /* get list of MemberReview as a LiveData based on the change of hetekaId and changing in value
    * of appRepository.getAllReviewsByHetekaId(id) */
    val allReviewsByHetekaId: LiveData<List<MemberReview>> = Transformations.switchMap(hetekaId) {
        id -> appRepository.getAllReviewsByHetekaId(id)
    }

    /* Get list of rating from list of all reviews
     */
    private val allRatings:LiveData<List<Float>> = Transformations.map(allReviewsByHetekaId) {
       listReview -> ParliamentFunctions.listRating(listReview)
    }

    /**
     * Get averageRating as a LiveData depending on the change of allRatings LiveData
     */
    val averageRating: LiveData<Float> = Transformations.map(allRatings) {
            it.average().toFloat()
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

