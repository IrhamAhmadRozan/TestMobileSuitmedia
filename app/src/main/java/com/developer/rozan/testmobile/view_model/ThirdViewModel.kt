package com.developer.rozan.testmobile.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.developer.rozan.testmobile.data.repository.UserRepository
import com.developer.rozan.testmobile.data.response.DataItem

class ThirdViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<DataItem>> =
        userRepository.getUser().cachedIn(viewModelScope)
}