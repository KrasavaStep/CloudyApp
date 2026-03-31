package com.example.common

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.domain.models.TokenModel
import com.example.domain.repositories.ITokenRepository
import androidx.core.content.edit
import com.google.gson.Gson


class EncryptedTokenRepositoryImpl(context: Context): ITokenRepository {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_tokens",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun getToken(serviceName: String): TokenModel {
        return Gson().fromJson(sharedPreferences.getString("auth_token_$serviceName", null), TokenModel::class.java)
    }

    override fun saveToken(token: TokenModel) {
        val tokenString = Gson().toJson(token)
        sharedPreferences.edit { putString("auth_token_${token.serviceName}", tokenString) }
    }

    override fun clearToken() {
        //TODO
    }
}