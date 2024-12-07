package com.example.myapplication

import android.app.Application
import com.example.myapplication.dependeciesinjection.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp // Fungsinya untuk menyimpan instance

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) // Membuat instance ContainerApp
            //Instance adalah obj yang dibuat dari class
    }
}