package br.com.nglauber.jetpackcomposeplayground.crud

import androidx.lifecycle.*
import br.com.nglauber.jetpacksample.repository.LocalRepository
import kotlinx.coroutines.launch

class AppViewModel(
    private val repo: LocalRepository
) : ViewModel() {

    val allUsers: LiveData<List<UserBinding>> = Transformations.map(
        repo.getAll().asLiveData()
    )
    { users ->
        users.map { UserBinding.fromUser(it) }
    }

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