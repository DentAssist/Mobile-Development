package com.dicoding.myevent.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.myevent.data.local.HistoryDao
import com.dicoding.myevent.data.local.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    companion object{
        @Volatile
        private var INSTANCE: Database? = null
        @JvmStatic
        fun getDatabase(context: Context):Database{
            if (INSTANCE == null){
                synchronized(Database::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        Database::class.java, "adatabase")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as Database
        }
    }
}