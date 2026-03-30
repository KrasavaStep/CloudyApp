package com.example.domain.repositories

import com.example.domain.models.FileModel

interface IFilesRepository {

    suspend fun getFiles(): FileModel?
    suspend fun saveFile(file: FileModel)

}