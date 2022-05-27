package com.example.androidstudy.any.coroutines.repository

import kotlinx.coroutines.delay

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.coroutines.repository
 * ClassName: UserRepository
 * CreateDate: 2022/5/23 4:07 下午
 * Author: zjy
 * Description:
 */
class UserRepository {
    suspend fun getUser(): String {
        delay(2000)
        return "zjy"
    }
}