package com.example.twittertest.presentation.screens.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_data.data.repository.RepositoryImpl
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
class SignUpViewModel @Inject constructor(
    private val repo: RepositoryImpl,
) : ViewModel() {
    val userFlow: MutableStateFlow<Resource<Any>> = MutableStateFlow(Resource.Loading)
    val user = userFlow.asStateFlow()
    var currentUsername: String = ""
    var currentPassword: String = ""
    var currentName: String = ""

    /**
     *
     */
    fun saveUser() {
        viewModelScope.launch {
            userFlow.value = repo.saveUser(currentName, currentUsername, currentPassword)
            when (userFlow.value) {
                is Resource.Error -> println("Error")
                Resource.Loading -> println("Loading")
                is Resource.Success -> println("$userFlow")

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

    /**
     *
     */
    fun setName(name: String) {
        currentName = name
    }
}

