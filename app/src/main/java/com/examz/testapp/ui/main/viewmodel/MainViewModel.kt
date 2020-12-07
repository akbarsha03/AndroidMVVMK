package com.examz.testapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.examz.testapp.data.model.Breed
import com.examz.testapp.data.repository.MainRepository
import com.examz.testapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    fun getBreeds(limit: Int = 10, page: Int = 1): LiveData<Resource<List<Breed>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = mainRepository.getBreeds(limit, page)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
            }
        }
    }
}