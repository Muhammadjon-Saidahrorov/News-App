package uz.gita.newsappmn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newsappmn.presentation.pages.diraction.Diraction
import uz.gita.newsappmn.presentation.pages.diraction.DiractionImpl

@Module
@InstallIn(SingletonComponent::class)
interface DiractionModule {
    @Binds
    fun bindDiraction(impl:DiractionImpl):Diraction
}