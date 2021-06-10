package uz.suhrob.zakovat.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.suhrob.zakovat.data.repository.Repository

class AuthViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}