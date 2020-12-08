package com.examz.testapp.di.module

import androidx.lifecycle.ViewModel
import com.examz.testapp.ui.detail.view.DetailActivity
import com.examz.testapp.ui.detail.viewmodel.DetailViewModel
import com.examz.testapp.ui.main.view.MainActivity
import com.examz.testapp.ui.main.viewmodel.MainViewModel
import com.examz.testapp.di.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [AppLayerModule::class])
object AppModule {

}

@Module
abstract class AppLayerModule {

    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun bindDetailActivity(): DetailActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

}
