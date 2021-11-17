package com.openweather.map.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openweather.map.core.Constants.NetworkService.DATABASE_NAME
import com.openweather.map.data.model.room.Weather
import com.openweather.map.data.model.room.WeatherDao
import com.openweather.map.ui.search.SearchActivity

/**
 * Created by Shweta on 15/11/21.
 */

@Database(entities = [Weather::class], version = 1)
abstract class DatabaseService : RoomDatabase() {

    companion object {

        @Volatile
        private var instance: DatabaseService? = null

        @JvmStatic
        fun getInstance(context: Context): DatabaseService = synchronized(this) {
            if (instance == null) instance = buildDatabase(context)
            return instance as DatabaseService
        }

        private fun buildDatabase(context: Context): DatabaseService = Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            DATABASE_NAME
        ).build()
    }

    abstract fun weatherDao(): WeatherDao
}
