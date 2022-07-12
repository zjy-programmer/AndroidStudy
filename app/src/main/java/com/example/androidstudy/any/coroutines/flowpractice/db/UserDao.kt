package com.example.androidstudy.any.coroutines.flowpractice.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * @author zjy
 * @date 2022/7/7 6:50 下午
 * @description
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM USER")
    fun getAll(): Flow<List<User>>

}