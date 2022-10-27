package com.example.twittertest.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_data.data.repository.RepositoryImpl
import com.example.lib_data.domain.models.Token
import com.example.lib_data.util.DataStorePrefSource
import com.example.lib_data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: RepositoryImpl,
    private val dataStorePrefImpl: DataStorePrefSource
) : ViewModel() {
    val userFlow: MutableStateFlow<Resource<Token>?> = MutableStateFlow(null)
    val user = userFlow.asStateFlow()
    var currentUsername: String = ""
    var currentPassword: String = ""

    /**
     *
     */
    fun loginUser() {
        viewModelScope.launch {
            userFlow.value = repo.loginUser(currentUsername, currentPassword)
            when (userFlow.value) {
                is Resource.Error -> println("Error")
                Resource.Loading -> println("Loading")
                is Resource.Success ->
                    dataStorePrefImpl.setPreferenceInfo((userFlow.value as Resource.Success<Token>).data.accessToken)


                null -> println("null")
            }

        }

    }

    /**
     *
     */
    fun setUsername(username: String) {
        currentUsername = username
    }

    /**
     *
     */
    fun setPassword(password: String) {
        currentPassword = password
    }
}
