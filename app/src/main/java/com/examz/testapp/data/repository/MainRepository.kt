package com.examz.testapp.data.repository

import com.examz.testapp.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getBreeds(limit: Int, page: Int) = apiHelper.getBreeds(limit, page)
}