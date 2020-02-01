package com.example.nozzletagconnect.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.util.*

@Entity(tableName = "stats")
data class ExeResultEntity (
    @ColumnInfo(name = "incorrectAnswersCount") var incorrectAnswersCount: Int,
    @ColumnInfo(name = "correctAnswersCount") var correctAnswersCount: Int,
    @ColumnInfo(name = "dateTime") var dateTime: Long
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(incorrectAnswersCount)
        parcel.writeInt(correctAnswersCount)
        parcel.writeLong(dateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExeResultEntity> {
        override fun createFromParcel(parcel: Parcel): ExeResultEntity {
            return ExeResultEntity(parcel)
        }

        override fun newArray(size: Int): Array<ExeResultEntity?> {
            return arrayOfNulls(size)
        }
    }
}