package com.example.nummerals.database

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.MediatorLiveData
import com.example.nozzletagconnect.db.DeviceRoomDatabase
import com.example.nozzletagconnect.db.entity.ExeResultEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object StatsRepository {
    private var deviceDao: DeviceDao? = null
    private val listData: List<ExeResultEntity> = listOf()
    private var context: Context?= null

    fun init(context: Context?) {
        this.context = context
        StatsRepository.context?.let {
            val deviceRoomDatabase = DeviceRoomDatabase.getInstance(it)
            deviceDao = deviceRoomDatabase?.deviceDao()!!
        }
    }


    fun getAll(callback: (List<ExeResultEntity>?) -> Unit) {
        GlobalScope.launch {
            callback.invoke(deviceDao?.getAllAsList())
        }
    }

    fun insert(device: ExeResultEntity) {
        deviceDao?.let {
            insertAsyncTask(it).execute(device)
        }
    }

    fun update(device: ExeResultEntity) {
        deviceDao?.let {
            updateAsyncTask(it).execute(device)
        }
    }

    fun delete(device: ExeResultEntity) {
        deviceDao?.let {
            deleteAsyncTask(it).execute(device)
        }
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: DeviceDao) : AsyncTask<ExeResultEntity, Void, Void>() {

        override fun doInBackground(vararg params: ExeResultEntity): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class updateAsyncTask internal constructor(private val mAsyncTaskDao: DeviceDao) : AsyncTask<ExeResultEntity, Void, Void>() {

        override fun doInBackground(vararg params: ExeResultEntity): Void? {
            mAsyncTaskDao.update(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: DeviceDao) : AsyncTask<ExeResultEntity, Void, Void>() {

        override fun doInBackground(vararg params: ExeResultEntity): Void? {
            mAsyncTaskDao.delete(params[0])
            return null
        }
    }

}