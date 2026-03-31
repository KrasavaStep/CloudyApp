package com.example.domain.usecases

import com.example.domain.models.FileModel
import com.example.domain.repositories.IFilesRepository

class GetFilesUseCase(val repository: IFilesRepository) {

    suspend operator fun invoke(): List<FileModel>? {
        return repository.getFiles()
    }

}