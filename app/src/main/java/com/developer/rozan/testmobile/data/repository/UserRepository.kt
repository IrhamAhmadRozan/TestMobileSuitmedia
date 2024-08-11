package com.developer.rozan.testmobile.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.developer.rozan.testmobile.data.local.UserRoomDatabase
import com.developer.rozan.testmobile.data.paging.UserPagingSource
import com.developer.rozan.testmobile.data.response.DataItem
import com.developer.rozan.testmobile.data.retrofit.ApiService

class UserRepository(
    private val userRoomDatabase: UserRoomDatabase,
    private val apiService: ApiService
) {
    fun getUser(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }
}