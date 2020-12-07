package com.examz.testapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.examz.testapp.data.api.ApiHelper
import com.examz.testapp.data.api.ApiService
import com.examz.testapp.data.model.Breed
import com.examz.testapp.data.repository.MainRepository
import com.examz.testapp.ui.main.viewmodel.MainViewModel
import com.examz.testapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<Breed>>>

    @Before
    fun setUp() {
        // do something if required
        apiHelper = ApiHelper(apiService)
        mainRepository = MainRepository(apiHelper)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {

            doReturn(emptyList<Breed>())
                .`when`(apiHelper)
                .getBreeds(10, 1)

            val viewModel = MainViewModel(mainRepository)
            viewModel.getBreeds().observeForever(apiUsersObserver)

            verify(apiHelper).getBreeds(10, 1)
            verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.getBreeds().removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {

            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getBreeds(10, 1)

            val viewModel = MainViewModel(mainRepository)
            viewModel.getBreeds(10, 1).observeForever(apiUsersObserver)
            verify(apiHelper).getBreeds(10, 1)

            verify(apiUsersObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.getBreeds().removeObserver(apiUsersObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }
}