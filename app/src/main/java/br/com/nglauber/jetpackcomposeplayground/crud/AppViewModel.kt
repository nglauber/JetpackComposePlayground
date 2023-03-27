package br.com.nglauber.jetpackcomposeplayground.crud

import androidx.lifecycle.*
import br.com.nglauber.jetpacksample.repository.LocalRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppViewModel(
    private val repo: LocalRepository
) : ViewModel() {

    val allUsers: LiveData<List<UserBinding>> = repo.getAll().map { users ->
        users.map { UserBinding.fromUser(it) }
    }.asLiveData()

    fun saveUser(userBinding: UserBinding) {
        viewModelScope.launch {
            if (userBinding.id == 0L) {
                repo.insert(userBinding.toUser())
            } else {
                repo.update(userBinding.toUser())
            }
        }
    }

    fun deleteUser(userBinding: UserBinding) {
        viewModelScope.launch {
            repo.delete(userBinding.toUser())
        }
    }
}