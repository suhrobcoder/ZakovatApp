package uz.suhrob.zakovat.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.suhrob.zakovat.data.pref.AppPrefs
import uz.suhrob.zakovat.data.repository.Repository

class TeamViewModelFactory(private val repository: Repository, private val pref: AppPrefs): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeamViewModel(repository, pref) as T
    }
}