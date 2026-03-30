package com.example.dropbox

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.example.domain.models.FileModel
import com.example.domain.repositories.IFilesRepository
import com.example.domain.usecases.GetTokenUseCase
import com.example.domain.util.Services
import com.google.gson.Gson

class DropBoxFileRepositoryImpl(
    val getTokenUseCase: GetTokenUseCase
): IFilesRepository {

    private var client: DbxClientV2? = null

    init {
        client = initDropboxClient()
    }
    private fun initDropboxClient(): DbxClientV2 {
        val config = DbxRequestConfig.newBuilder("my-cool-app").build()
        val token = getTokenUseCase(Services.DropBox.serviceName)
        val credential = DbxCredential(token.accessToken, token.expiresAt, token.refreshToken, token.appKey)
        return DbxClientV2(config, credential)
    }

    override suspend fun getFiles(): FileModel? {
        return client?.let {
            FileModel("test file")
        }
    }

    override suspend fun saveFile(file: FileModel) {
        TODO("Not yet implemented")
    }
}