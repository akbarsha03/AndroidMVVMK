package com.examz.testapp.di.module

import com.examz.testapp.data.api.ApiHelper
import com.examz.testapp.data.repository.DetailRepository
import com.examz.testapp.data.repository.MainRepository
import dagger.Module
import dagger.Provides

@Module(includes = [ProductLayerModule::class])
object ProductModule {
    
    @Provides
    fun provideMainRepository(apiHelper: ApiHelper): MainRepository {
        return MainRepository(apiHelper)
    }

    @Provides
    fun provideDetailRepository(apiHelper: ApiHelper): DetailRepository {
        return DetailRepository(apiHelper)
    }
}

@Module
abstract class ProductLayerModule {


}