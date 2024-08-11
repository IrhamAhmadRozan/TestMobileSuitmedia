package com.developer.rozan.testmobile.di

import android.content.Context
import com.developer.rozan.testmobile.data.local.UserRoomDatabase
import com.developer.rozan.testmobile.data.repository.UserRepository
import com.developer.rozan.testmobile.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserRoomDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}