package com.example.cards

import android.app.Application
import android.os.Bundle
import timber.log.Timber

class CardsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}