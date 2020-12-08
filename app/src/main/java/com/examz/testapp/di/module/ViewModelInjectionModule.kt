package com.examz.testapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.examz.testapp.di.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelInjectionModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}