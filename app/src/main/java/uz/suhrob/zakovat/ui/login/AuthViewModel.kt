package uz.suhrob.zakovat.ui.login

import androidx.lifecycle.ViewModel
import uz.suhrob.zakovat.data.model.User
import uz.suhrob.zakovat.data.repository.Repository

class AuthViewModel(private val repository: Repository): ViewModel() {
    suspend fun auth(login: String, password: String): Boolean {
        return repository.authenticateUser(User(login, password))
    }

    suspend fun register(login: String, password: String) {
        repository.addTeam(login, password)
    }
}