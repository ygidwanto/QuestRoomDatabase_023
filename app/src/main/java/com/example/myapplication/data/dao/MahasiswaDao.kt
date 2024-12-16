package com.example.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.myapplication.data.entity.Mahasiswa

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
}