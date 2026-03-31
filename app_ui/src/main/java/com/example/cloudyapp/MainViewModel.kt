package com.example.cloudyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.FileModel
import com.example.domain.usecases.GetFilesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @field:Named("GetDropbox") private val getFilesDropbox: GetFilesUseCase
) : ViewModel() {

    private val _filesFlow = MutableStateFlow<List<FileModel>>(emptyList())
    val filesFlow = _filesFlow.asStateFlow()

    fun getFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _filesFlow.value = getFilesDropbox() ?: emptyList()
        }
    }

}