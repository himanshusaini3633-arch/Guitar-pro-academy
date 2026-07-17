package com.guitarproacademy.app

import android.app.Application
import com.guitarproacademy.app.data.local.AppDatabase
import com.guitarproacademy.app.data.repository.ProgressRepository

class GuitarProAcademyApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var progressRepository: ProgressRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
        progressRepository = ProgressRepository(database)
    }
}
