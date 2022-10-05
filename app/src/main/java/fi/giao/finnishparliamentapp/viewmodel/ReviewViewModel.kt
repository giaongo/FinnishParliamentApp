package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.MemberReview
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This view model queries the insert, update and delete review
 */
class ReviewViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    fun insertReview(review: MemberReview) = viewModelScope.launch {
        appRepository.insertReview(review)
    }
    fun updateReview(review: MemberReview) = viewModelScope.launch {
        appRepository.updateReview(review)
    }
    fun deleteReview(review: MemberReview) = viewModelScope.launch {
        appRepository.deleteReview(review)
    }

}

class ReviewViewModelFactory(val app:Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            ReviewViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}