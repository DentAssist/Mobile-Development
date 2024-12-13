package com.dicoding.asclepius.repository

import android.app.Application
import com.dicoding.myevent.data.local.Database
import com.dicoding.myevent.data.local.Database
import com.dicoding.myevent.data.local.HistoryEntity

class HistoryRepository(application: Application) {
    private val historyDao = Database.getDatabase(application).historyDao()

    suspend fun addHistory(historyEntity: HistoryEntity) {
        historyDao.addHistory(historyEntity)
    }

    fun getHistory(): List<HistoryEntity> = historyDao.getHistory()
}