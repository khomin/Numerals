package com.example.nozzletagconnect.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.nozzletagconnect.db.entity.ExeResultEntity
import com.example.nummerals.database.DeviceDao

@Database(entities = [ExeResultEntity::class], version = 7)
abstract class DeviceRoomDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao

    companion object {
        private var INSTANCE: DeviceRoomDatabase? = null

        fun getInstance(context: Context): DeviceRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(DeviceRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        DeviceRoomDatabase::class.java, "device.db")
                        .fallbackToDestructiveMigration() //TODO убрать в продакшен!!
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}