package com.stdio.yesno.data

import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val mainService: MainService) {

    suspend fun getAvailableBanks() = mainService.getAvailableBanks()
}