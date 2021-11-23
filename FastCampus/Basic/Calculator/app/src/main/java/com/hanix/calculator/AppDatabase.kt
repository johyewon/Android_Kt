package com.hanix.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanix.calculator.dao.HistoryDao
import com.hanix.calculator.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao       // AppDatabase 를 참조해 historyDao 를 사용할 수 있게 함
}