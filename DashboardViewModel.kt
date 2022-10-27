package com.example.twittertest.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_data.data.repository.RepositoryImpl
//import androidx.lifecycle.viewModelScope
//import com.example.lib_data.data.repository.RepositoryImpl
import com.example.lib_data.domain.models.Post
import com.example.lib_data.util.DataStorePrefSource
//import com.example.lib_data.util.DataStorePrefSource
import com.example.lib_data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 *
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: RepositoryImpl,
    private val dataStore: DataStorePrefSource
) : ViewModel() {
    private val _post: MutableStateFlow<Resource<List<Post>>> = MutableStateFlow(Resource.Loading)
    val posts = _post.asStateFlow()


    init {
        getPosts()
    }

    /**
     *
     *
     */
    fun getPosts() {
        viewModelScope.launch {
            val token = dataStore.getPreferenceInfo().first()
            _post.value = repo.getPosts("Bearer $token")
            when (_post.value) {
                is Resource.Error -> {}
                Resource.Loading -> {}
                is Resource.Success -> (_post.value as Resource.Success<List<Post>>).data
            }
        }
    }
}
