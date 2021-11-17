package com.openweather.map.ui.shared

import androidx.lifecycle.ViewModel
import com.openweather.map.data.Repository

/**
 * Created by Shweta on 15/11/21.
 */

class SharedViewModel(private val repository: Repository) : ViewModel() {

    override fun onCleared() {
        repository.clear()
        super.onCleared()
    }

    fun search(
        cityName: String,
        apiKey: String,
        error: (String) -> Unit
    ) = repository.search(cityName, apiKey, error)
    fun toggleSaved(id: Long) = repository.toggleSaved(id)

    fun observeSearch() = repository.observeSearch()
    fun observeSaved() = repository.observeSaved()
}
