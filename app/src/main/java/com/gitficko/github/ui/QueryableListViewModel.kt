package com.gitficko.github.ui

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import java.util.stream.Collectors

abstract class QueryableListViewModel<T> : ViewModel() {
    val list = MutableStateFlow<List<T>>(emptyList())
    protected val query = MutableStateFlow("")

    val suitableList = combine(list, query) { list, query ->
        list.stream().filter { item ->
            validateItem(item, query)
        }.collect(Collectors.toList())
    }

    fun updateQuery(query: String) {
        this.query.value = query
    }

    abstract fun validateItem(item: T, query: String): Boolean;
}
