package me.lab.happyprimo.data.repositories

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ServiceAPI {

//    @GET("feed/@Tarn1997")
    @GET("feed/@primoapp")
    @Headers("Accept: application/xml")
    suspend fun getFeed(
    ): Response<ResponseBody>

}