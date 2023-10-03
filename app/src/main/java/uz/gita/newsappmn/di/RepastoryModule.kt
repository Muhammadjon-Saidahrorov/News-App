package uz.gita.newsappmn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newsappmn.domain.NewsRepastory
import uz.gita.newsappmn.domain.NewsRepastoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepastoryModule {
    @[Binds Singleton]
    fun bindRepastory(impl:NewsRepastoryImpl):NewsRepastory
}