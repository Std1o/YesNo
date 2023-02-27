package com.stdio.yesno.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.stdio.yesno.data.MainRepository
import com.stdio.yesno.domain.models.Bank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankListViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel<List<Bank>>() {

    init {
        getAvailableBanks()
    }

    private fun getAvailableBanks() {
        viewModelScope.launch {
            launchRequest(repository.getAvailableBanks())
        }
    }
}