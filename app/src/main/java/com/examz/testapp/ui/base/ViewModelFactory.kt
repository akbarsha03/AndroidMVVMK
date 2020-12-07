package com.examz.testapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examz.testapp.data.api.ApiHelper
import com.examz.testapp.data.repository.DetailRepository
import com.examz.testapp.data.repository.MainRepository
import com.examz.testapp.ui.detail.viewmodel.DetailViewModel
import com.examz.testapp.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(DetailRepository(apiHelper)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}