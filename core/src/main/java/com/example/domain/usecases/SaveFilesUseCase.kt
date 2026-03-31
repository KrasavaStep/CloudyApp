package com.example.domain.usecases

import com.example.domain.models.FileModel
import com.example.domain.repositories.IFilesRepository

class SaveFilesUseCase (val repository: IFilesRepository) {

    suspend operator fun invoke(file: FileModel) {
        return repository.saveFile(file)
    }

}