package com.stdio.yesno.data

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) : BaseRepository() {

    suspend fun getAvailableBanks() =
        flow { emit(apiCall { remoteDataSource.getAvailableBanks() }) }
}