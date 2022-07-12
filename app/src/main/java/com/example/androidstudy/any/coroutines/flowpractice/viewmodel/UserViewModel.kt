package com.example.androidstudy.any.coroutines.flowpractice.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstudy.any.coroutines.flowpractice.db.AppDatabase
import com.example.androidstudy.any.coroutines.flowpractice.db.User
import com.example.baselibrary.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @author zjy
 * @date 2022/7/7 7:02 下午
 * @description
 */
class UserViewModel(app: Application): AndroidViewModel(app) {
    fun insert(uid: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            AppDatabase.getInstance(getApplication())
                .userDao()
                .insert(User(uid.toInt(), firstName, lastName))
            LogUtil.e("zjy", "insert user $uid")
        }
    }

    fun getAll(): Flow<List<User>> {
        return AppDatabase.getInstance(getApplication())
            .userDao()
            .getAll()
            .catch { e ->
                e.printStackTrace()
            }
            .flowOn(Dispatchers.IO)
    }
}