package com.planatech.voistask.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.planatech.voistask.main.model.Image
import com.planatech.voistask.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository): ViewModel() {

    fun getImages(): Flow<PagingData<Image>> {
        return mainRepository.getImages().cachedIn(viewModelScope)
    }
}