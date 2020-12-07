package com.examz.testapp.data.repository

import com.examz.testapp.data.api.ApiHelper

class DetailRepository(private val apiHelper: ApiHelper) {
    suspend fun getBreedByID(id: String) = apiHelper.getBreedById(id)
    suspend fun getBreedImage(id: String) = apiHelper.getBreedImage(id)
}