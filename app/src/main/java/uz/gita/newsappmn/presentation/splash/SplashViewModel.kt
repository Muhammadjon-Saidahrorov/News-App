package uz.gita.newsappmn.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.newsappmn.presentation.home.HomeScreen
import uz.gita.newsappmn.presentation.pages.diraction.Diraction
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val diraction: Diraction
):ViewModel() {
    fun navigate(){
        viewModelScope.launch {
            diraction.replace(HomeScreen())
        }
    }
}