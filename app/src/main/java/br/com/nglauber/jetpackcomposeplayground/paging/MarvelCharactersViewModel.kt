package br.com.nglauber.jetpackcomposeplayground.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import br.com.nglauber.jetpackcomposeplayground.paging.model.api.MarvelApi
import br.com.nglauber.jetpackcomposeplayground.paging.model.paging.MarvelApiPagingSource

class MarvelCharactersViewModel : ViewModel() {
    val charactersPagedListFlow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = MarvelApiPagingSource.PAGE_SIZE)
    ) {
        MarvelApiPagingSource(MarvelApi.getService())
    }.flow.cachedIn(viewModelScope)
}