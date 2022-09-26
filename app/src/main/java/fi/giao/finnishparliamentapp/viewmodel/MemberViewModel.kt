package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MemberViewModel(application: Application): AndroidViewModel(application) {

    private val appRepository =  AppRepository(AppDatabase.getInstance(application))

    val memberList:LiveData<List<ParliamentMember>> =  appRepository.getAllMembers()

    private val _status = MutableLiveData<String>()
    val status:LiveData<String> = _status

    init {
        getDataFromNetworkAndSave()
    }

    private fun getDataFromNetworkAndSave() {
        viewModelScope.launch {
            try {
                appRepository.getDataFromNetworkAndSave()
                _status.value = "fetching data successfully"
            } catch (error:IOException) {
                _status.value = error.toString()
            }
        }
    }

}

