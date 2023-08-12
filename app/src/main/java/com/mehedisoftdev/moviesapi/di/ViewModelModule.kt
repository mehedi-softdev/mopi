package com.mehedisoftdev.moviesapi.di

import androidx.lifecycle.ViewModel
import com.mehedisoftdev.moviesapi.viewmodels.MainViewModel
import com.mehedisoftdev.moviesapi.viewmodels.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @ClassKey(MainViewModel::class)
    @IntoMap
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @ClassKey(MovieDetailsViewModel::class)
    @IntoMap
    abstract fun movieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel
}