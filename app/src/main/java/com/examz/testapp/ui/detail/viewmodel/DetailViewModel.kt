package com.examz.testapp.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.examz.testapp.data.model.Breed
import com.examz.testapp.data.model.BreedWithImage
import com.examz.testapp.data.repository.DetailRepository
import com.examz.testapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository) :
    ViewModel() {
    fun getBreedByID(id: String): LiveData<Resource<Breed>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = detailRepository.getBreedByID(id)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, msg = exception.message ?: "Error occurred!"))
            }
        }
    }

    fun getBreedImage(id: String): LiveData<Resource<BreedWithImage>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = detailRepository.getBreedImage(id)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, msg = exception.message ?: "Error occurred!"))
            }
        }
    }
}