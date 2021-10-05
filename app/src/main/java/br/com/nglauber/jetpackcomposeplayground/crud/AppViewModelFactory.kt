package br.com.nglauber.jetpackcomposeplayground.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.nglauber.jetpacksample.repository.LocalRepository

class AppViewModelFactory(
    private val repo: LocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(repo) as T
    }
}