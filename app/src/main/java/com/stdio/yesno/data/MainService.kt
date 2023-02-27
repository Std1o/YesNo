package com.stdio.yesno.data

import com.stdio.yesno.domain.models.Bank
import retrofit2.Response
import retrofit2.http.POST

interface MainService {

    @POST("bank/available")
    suspend fun getAvailableBanks(): Response<List<Bank>>
}