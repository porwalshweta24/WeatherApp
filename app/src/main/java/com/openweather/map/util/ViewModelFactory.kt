package com.openweather.map.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openweather.map.data.Repository
import com.openweather.map.ui.shared.SharedViewModel

/**
 * Created by Shweta on 15/11/21.
 */

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(repository) as T
    }
}
