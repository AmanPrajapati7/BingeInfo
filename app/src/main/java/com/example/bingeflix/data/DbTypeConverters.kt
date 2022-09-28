package com.example.bingeflix.data

import androidx.room.TypeConverter
import java.util.*

class DbTypeConverters {

    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return if(value == null) return null else Date(value)
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }

}