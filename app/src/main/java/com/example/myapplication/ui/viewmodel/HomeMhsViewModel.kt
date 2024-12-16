package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entity.Mahasiswa
import com.example.myapplication.repository.RepositoryMhs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted

class HomeMhsViewModel(
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    val homeUIState: StateFlow<HomeUiState> = repositoryMhs.getAllMhs()
        .filterNotNull() // Pastikan repository mengembalikan data non-null
        .map { listMahasiswa ->
            HomeUiState(
                listMhs = listMahasiswa.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUiState(isLoading = true))
            delay(900) // Delay untuk simulasi loading
        }
        .catch { exception ->
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(isLoading = true)
        )
}

data class HomeUiState(
    val listMhs: List<Mahasiswa> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
