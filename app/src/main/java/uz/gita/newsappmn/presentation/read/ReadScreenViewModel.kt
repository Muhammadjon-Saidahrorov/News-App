package uz.gita.newsappmn.presentation.read

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.newsappmn.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class ReadScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator
):ViewModel() {
    fun back(){
        viewModelScope.launch {
            appNavigator.back()
        }
    }
}