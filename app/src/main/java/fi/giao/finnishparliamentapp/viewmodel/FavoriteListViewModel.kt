package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import android.text.method.TransformationMethod
import android.util.Log
import androidx.lifecycle.*
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FavoriteListViewModel(application: Application):AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))
    private val _status = MutableLiveData<String>()
    val status = _status

    val listHetekaId: LiveData<List<Int>> = Transformations.map(appRepository.getAllFavorite()) {
        ParliamentFunctions.listHetekaId(it)
    }

    fun getTestMember(list:List<Int>?):List<ParliamentMember>? {
        if (list != null) {
            val result = runBlocking {
                 appRepository.getFavoriteParliamentMember(list)
            }
            return result
        }
        return null
    }

    fun print() {
        Log.d("TEST","Printing")
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