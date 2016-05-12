package com.bulbstudios.justeat.services

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Terence Baker on 12/05/2016.
 */
interface JustEatService {

    @GET("restaurants")
    fun getResturantsWithPostcode(@Query("q") postcode: String): Observable<JsonObject>
}