package com.examz.testapp.data.api

import com.examz.testapp.data.model.Breed
import com.examz.testapp.data.model.BreedWithImage
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v1/breeds") // Extract version
    suspend fun getBreeds(
        @Query("limit") limit: Int = ApiConstants.PAGE_LIMIT,
        @Query("page") page: Int = ApiConstants.PAGE_NUMBER
    ): List<Breed>

    @GET("/v1/breeds/{id}")
    suspend fun getBreedByID(
        @Path("id") limit: String
    ): Breed

    @GET("/v1/images/{id}")
    suspend fun getBreedImage(
        @Path("id") limit: String
    ): BreedWithImage
}