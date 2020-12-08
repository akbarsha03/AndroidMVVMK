package com.examz.testapp.di

import android.app.Application
import com.examz.testapp.ExamzApplication
import com.examz.testapp.di.module.ApiModule
import com.examz.testapp.di.module.AppModule
import com.examz.testapp.di.module.ProductModule
import com.examz.testapp.di.module.ViewModelInjectionModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        ViewModelInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ProductModule::class]
)
interface AppComponent : AndroidInjector<ExamzApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}