package com.examz.testapp.di.module

import com.examz.testapp.BuildConfig
import com.examz.testapp.data.api.ApiConstants
import com.examz.testapp.data.api.ApiHelper
import com.examz.testapp.data.api.ApiService
import com.examz.testapp.di.utils.BaseConfig
import com.examz.testapp.di.utils.NetworkConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(
                Interceptor { chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader(ApiConstants.API_KEY_PARAM, BuildConfig.API_KEY)
                        .build()
                    return@Interceptor chain.proceed(request)
                }
            )

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(httpLoggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(
                NetworkConfig.getBaseUrl(
                    BaseConfig.currentEnvironment
                )
            )
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelper(apiService)
    }
}