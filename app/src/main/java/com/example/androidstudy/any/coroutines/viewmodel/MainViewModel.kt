package com.example.androidstudy.any.coroutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstudy.any.coroutines.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.coroutines.viewmodel
 * ClassName: MainViewModel
 * CreateDate: 2022/5/23 3:53 下午
 * Author: zjy
 * Description:
 */
class MainViewModel : ViewModel() {
    val userLiveData = MutableLiveData<String>()

    private val userRepository = UserRepository()

    fun getUser() {
        viewModelScope.launch {
            userLiveData.value = userRepository.getUser()
        }
    }
}