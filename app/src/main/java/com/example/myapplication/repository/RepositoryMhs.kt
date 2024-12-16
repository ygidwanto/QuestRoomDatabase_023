package com.example.myapplication.repository

import com.example.myapplication.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
    fun getMhs(string: kotlin.String)
}