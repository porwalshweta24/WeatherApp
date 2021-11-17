package com.openweather.map.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.openweather.map.R
import com.openweather.map.core.Constants.NetworkService.API_KEY
import com.openweather.map.core.Constants.NetworkService.BASE_URL
import com.openweather.map.data.Repository
import com.openweather.map.data.local.DatabaseService
import com.openweather.map.data.remote.Networking
import com.openweather.map.ui.shared.SharedViewModel
import com.openweather.map.ui.saved.SavedActivity
import com.openweather.map.ui.shared.SharedAdapter
import com.openweather.map.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by Shweta on 15/11/21.
 */

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = initViewModel()
        viewModel.observeSearch().observe(this, { weather ->
            weather?.let {
                toggleProgress(false)
                recycler_view.adapter = SharedAdapter(listOf(it)) { weatherId ->
                    viewModel.toggleSaved(weatherId)
                }
            }
        })

        saved.setOnClickListener { startActivity(Intent(this, SavedActivity::class.java)) }

        input_find_city_weather.isActivated = true
        input_find_city_weather.setIconifiedByDefault(false)
        input_find_city_weather.isIconified = false

        input_find_city_weather.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String): Boolean {
                    if (newText.isNotEmpty() && newText.count() > 2) {

                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty() == true && newText.count() > 2) {
                        viewModel.search(newText, API_KEY) { error ->
                            toggleProgress(false)
                            showToast(error)
                        }
                    }
                    return true
                }
            }
        )

        /*input_find_city_weather.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                toggleProgress(true)

                if (!Networking.isNetworkConnected(this)) {
                    toggleProgress(false)
                    showToast(getString(R.string.internet_connecvity_error_msg))
                }else {
                    val cityName: String = input_find_city_weather.text.toString()//(view as EditText).text.toString()
                    if (cityName.isBlank()) {
                        toggleProgress(false)
                        showToast(getString(R.string.enter_city_name))
                    }else {
                        if (cityName?.isNotEmpty() && cityName.count() > 2) {
                            viewModel.search(cityName, API_KEY) { error ->
                                toggleProgress(false)
                                showToast(error)
                            }
                        }
                    }
                }
            }
            false
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.observeSearch().removeObservers(this)
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

    private fun toggleProgress(enable: Boolean) {
        if (enable) {
            progress.visibility = View.VISIBLE
            recycler_view.visibility = View.GONE
        } else {
            progress.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}
