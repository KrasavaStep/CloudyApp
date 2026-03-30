package com.example.domain.repositories

import com.example.domain.models.TokenModel

interface ITokenRepository {

    fun getToken(serviceName: String): TokenModel
    fun saveToken(token: TokenModel)
    fun clearToken()

}