package com.example.restaurantreviewer.Database.Room

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.ZoneId

class RestaurantConverters {
    
    @TypeConverter
    fun fromDate(date: LocalDate?): Long? {
        val zoneId: ZoneId = ZoneId.systemDefault()
        return date?.atStartOfDay(zoneId)?.toEpochSecond()
    }

    @TypeConverter
    fun toDate(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }
}