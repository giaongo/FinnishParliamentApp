package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.repository.AppRepository

class MemberInfoViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase.getInstance(application))

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

