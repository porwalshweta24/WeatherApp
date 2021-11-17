package com.openweather.map.ui.saved

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openweather.map.R
import com.openweather.map.core.Constants.NetworkService.BASE_URL
import com.openweather.map.data.Repository
import com.openweather.map.data.local.DatabaseService
import com.openweather.map.data.remote.Networking
import com.openweather.map.ui.search.SearchActivity
import com.openweather.map.ui.shared.SharedAdapter
import com.openweather.map.ui.shared.SharedViewModel
import com.openweather.map.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_saved.*
import kotlinx.android.synthetic.main.activity_search.recycler_view

/**
 * Created by Shweta on 15/11/21.
 */

class SavedActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        viewModel = initViewModel()
        viewModel.observeSaved().observe(this, { weatherList ->
            recycler_view.adapter = SharedAdapter(weatherList) { weatherId ->
                viewModel.toggleSaved(weatherId)
            }
        })

        back.setOnClickListener { onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.observeSaved().removeObservers(this)
    }

    // endregion Lifecycle

    private fun initViewModel(): SharedViewModel {
        val model: SharedViewModel by viewModels {
            ViewModelFactory(Repository(
                DatabaseService.getInstance(this),
                Networking.create(BASE_URL)
            ))
        }
        return model
    }
}
