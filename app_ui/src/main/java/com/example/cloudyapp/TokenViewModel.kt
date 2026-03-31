package com.example.cloudyapp

import androidx.lifecycle.ViewModel
import com.dropbox.core.oauth.DbxCredential
import com.example.domain.models.TokenModel
import com.example.domain.usecases.SaveTokenUseCase
import com.example.domain.util.Services
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    val saveTokenUseCase: SaveTokenUseCase
): ViewModel() {

    fun saveDropboxAuthToken(credential: DbxCredential) {
        val token = TokenModel(
            credential.accessToken,
            credential.expiresAt,
            credential.refreshToken,
            credential.appKey,
            Services.DropBox.serviceName
        )
        saveTokenUseCase(token)
    }

}