package com.example.nummerals.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nozzletagconnect.db.entity.ExeResultEntity

@Dao
interface DeviceDao {

    @Query("SELECT * FROM stats")
    fun getAll(): LiveData<List<ExeResultEntity>>

    @Query("SELECT * FROM stats")
    fun getAllAsList(): List<ExeResultEntity>

    @Insert
    fun insert(device: ExeResultEntity)

    @Insert
    fun insertAll(vararg devices: ExeResultEntity)

    @Update
    fun update(device: ExeResultEntity)

    @Delete
    fun delete(device: ExeResultEntity)
}