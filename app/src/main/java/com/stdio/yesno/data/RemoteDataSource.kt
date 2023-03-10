package com.stdio.yesno.data

import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val mainService: MainService) {

    suspend fun getYesNoResult() = mainService.getYesNoResult()

    suspend fun getYesNoResult(force: String) = mainService.getYesNoResult(force)
}