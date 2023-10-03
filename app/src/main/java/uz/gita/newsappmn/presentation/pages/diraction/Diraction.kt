package uz.gita.newsappmn.presentation.pages.diraction

import uz.gita.newsappmn.navigation.AppNavigator
import uz.gita.newsappmn.navigation.AppScreen
import javax.inject.Inject

interface Diraction {
    suspend fun navigateTo(screen: AppScreen)
    suspend fun replace(screen: AppScreen)
}

class DiractionImpl @Inject constructor(
    private val appNavigator: AppNavigator
): Diraction {
    override suspend fun navigateTo(screen: AppScreen) {
        appNavigator.navigateTo(screen)
    }

    override suspend fun replace(screen: AppScreen) {
        appNavigator.replace(screen)
    }

}