package com.example.myapplication.ui.viewmahasiswa

import android.R.attr.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.myapplication.ui.costumwidget.TopAppBar
import com.example.myapplication.ui.navigation.AlamatNavigasi
import com.example.myapplication.ui.viewmodel.FormErrorState
import com.example.myapplication.ui.viewmodel.MahasiswaEvent
import com.example.myapplication.ui.viewmodel.MahasiswaViewModel
import com.example.myapplication.ui.viewmodel.MhsUIState
import com.example.myapplication.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch



object DestinasiInsert: AlamatNavigasi{
    override val route: String = "insert_mhs"
}

@Composable
fun InserMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState // Ambil UI state dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { mesage ->
            coroutineScope.launch{
                snackbarHostState.showSnackbar(mesage)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mahasiswa"
            )
            // Isi Body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch{
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }

    }
}
@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: MhsUIState,
    onClick: () -> Unit
){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMahasiswa(
            mahasiswaEvent = uiState.mahasiswaEvent,
            onValueChange = onValueChange,
            erorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),

        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")

        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit ={},
    erorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){

    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(nama =it))
            },
            label = { Text("Nama") },
            isError = erorState.nama != null,
            placeholder = {Text("Masukkan Nama")},
        )
        Text(
            text = erorState.nama ?:"",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(nim =it))
            },
            label = { Text("NIM") },
            isError = erorState.nim != null,
            placeholder = {Text("Masukkan NIM")},
        )
        Text(
            text = erorState.nim ?:"",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            jenisKelamin.forEach { jk ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(jenisKelamin = jk))
                        },
                    )
                    Text(
                        text = jk,
                    )
                }
            }
        }
        Text(
            text = erorState.jenisKelamin ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            isError = erorState.alamat != null,
            placeholder = {Text("Masukkan Alamat")},
        )
        Text(
            text = erorState.alamat ?:"",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Kelas")
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            kelas.forEach { kls->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kls,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas= kls))
                        },
                    )
                    Text(
                        text = kls,
                    )
                }
            }
        }
        Text(
            text = erorState.kelas ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(angkatan =it))
            },
            label = { Text("Angkatan") },
            isError = erorState.angkatan != null,
            placeholder = {Text("Masukkan angkatan anda")},
        )
        Text(
            text = erorState.angkatan ?:"",
            color = Color.Red
        )

    }
}