package com.stdio.yesno.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.stdio.yesno.data.MainRepository
import com.stdio.yesno.domain.models.AnswerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel<AnswerResult>() {

    var imageUrl: String? = null

    init {
        getYesNoResult()
    }

    fun getYesNoResult() {
        viewModelScope.launch {
            launchRequest(repository.getYesNoResult())
        }
    }

    fun getYesNoResult(force: String) {
        viewModelScope.launch {
            launchRequest(repository.getYesNoResult(force))
        }
    }
}