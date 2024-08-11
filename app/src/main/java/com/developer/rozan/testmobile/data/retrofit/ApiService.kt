package com.developer.rozan.testmobile.data.retrofit

import com.developer.rozan.testmobile.data.response.AllUserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getAllUser(
        @Query("page") page: Int? = null,
        @Query("per_page") size: Int? = null
    ): AllUserResponse
}