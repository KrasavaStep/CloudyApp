package com.example.dropbox

import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.example.domain.models.FileModel
import com.example.domain.repositories.IFilesRepository
import com.example.domain.usecases.GetTokenUseCase
import com.example.domain.util.Services
import javax.inject.Inject

class DropBoxFileRepositoryImpl @Inject constructor(
    val getTokenUseCase: GetTokenUseCase
): IFilesRepository {

    private var client: DbxClientV2? = null

    init {
        client = initDropboxClient()
    }
    private fun initDropboxClient(): DbxClientV2 {
        //TODO change string to variable
        val config = DbxRequestConfig.newBuilder("CloudyApp/1.0.0").build()
        val token = getTokenUseCase(Services.DropBox.serviceName)
        val credential = DbxCredential(token.accessToken, token.expiresAt, token.refreshToken, token.appKey)
        return DbxClientV2(config, credential)
    }

    override suspend fun getFiles(): List<FileModel> {
        val fileList = mutableListOf<FileModel>()
        try {
            // 1. Получаем первую порцию данных
            var result = client?.files()?.listFolder("")
            while (true) {
                if (result != null) {
                    for (metadata in result.entries) {
                        // Проверяем тип объекта: файл или папка
                        when (metadata) {
                            is FileMetadata -> fileList.add(FileModel("Файл: ${metadata.pathLower} (Размер: ${metadata.size})"))
                            is FolderMetadata -> fileList.add(FileModel("Папка: ${metadata.pathLower}"))
                        }
                    }

                    // 2. Если есть еще данные, запрашиваем следующую порцию по курсору
                    if (!result.hasMore) break
                    result = client?.files()?.listFolderContinue(result.cursor)
                }
            }
        } catch (e: DbxException) {
            e.printStackTrace()
        }
        return fileList
    }

    override suspend fun saveFile(file: FileModel) {
        TODO("Not yet implemented")
    }
}