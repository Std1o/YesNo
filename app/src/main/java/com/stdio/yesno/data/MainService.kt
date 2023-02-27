package com.stdio.yesno.data

import com.stdio.yesno.domain.models.AnswerResult
import retrofit2.Response
import retrofit2.http.GET

interface MainService {

    @GET("api")
    suspend fun getYesNoResult(): Response<AnswerResult>
}