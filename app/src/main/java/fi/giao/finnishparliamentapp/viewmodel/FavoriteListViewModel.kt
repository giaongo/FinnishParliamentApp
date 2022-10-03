package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import android.text.method.TransformationMethod
import android.util.Log
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.*


class FavoriteListViewModel(application: Application):AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val _favoriteList = MutableLiveData<List<ParliamentMember>>()
    val favoriteList: LiveData<List<ParliamentMember>> = _favoriteList

    val listHetekaId: LiveData<List<Int>> = Transformations.map(appRepository.getAllFavorite()) {
        ParliamentFunctions.listHetekaId(it)
    }

    fun getFavoriteMemberList(list:List<Int>) {
        viewModelScope.launch {
            _favoriteList.value  = appRepository.getFavoriteParliamentMember(list)
        }
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