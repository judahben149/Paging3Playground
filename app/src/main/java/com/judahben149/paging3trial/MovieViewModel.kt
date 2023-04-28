package com.judahben149.paging3trial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val moviesList = Pager(
        PagingConfig(
            initialLoadSize = 60,
            pageSize = 20,
            prefetchDistance = 5
        )
    ) { MoviesPagingDataSource(repository) }.flow.cachedIn(viewModelScope)
}