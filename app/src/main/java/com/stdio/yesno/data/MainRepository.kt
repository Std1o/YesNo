package com.stdio.yesno.data

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) : BaseRepository() {

    suspend fun getYesNoResult() =
        flow { emit(apiCall { remoteDataSource.getYesNoResult() }) }

    suspend fun getYesNoResult(force: String) =
        flow { emit(apiCall { remoteDataSource.getYesNoResult(force) }) }
}