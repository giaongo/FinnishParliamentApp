package fi.giao.finnishparliamentapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fi.giao.finnishparliamentapp.database.AppDatabase
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.repository.AppRepository

class MemberViewModel(application: Application): AndroidViewModel(application) {

    private val appRepository =  AppRepository(AppDatabase.getInstance(application))

    private val _memberList = MutableLiveData<List<ParliamentMember>>()
    val memberList: LiveData<List<ParliamentMember>> = _memberList
}

