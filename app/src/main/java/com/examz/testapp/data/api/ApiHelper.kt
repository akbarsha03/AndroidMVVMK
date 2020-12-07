package com.examz.testapp.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getBreeds(limit: Int, page: Int) = apiService.getBreeds(limit, page)
    suspend fun getBreedById(id: String) = apiService.getBreedByID(id)
    suspend fun getBreedImage(id: String) = apiService.getBreedImage(id)
}