package com.example.myapplication.data.dao

import androidx.room.Insert
import com.example.myapplication.data.entity.Mahasiswa

interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
}