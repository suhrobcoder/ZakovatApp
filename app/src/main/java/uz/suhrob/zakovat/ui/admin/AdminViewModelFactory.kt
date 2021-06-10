package uz.suhrob.zakovat.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.suhrob.zakovat.data.repository.Repository

class AdminViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdminViewModel(repository) as T
    }
}