package com.example.domain.models

data class TokenModel(
    val accessToken: String?,
    val expiresAt: Long?,
    val refreshToken: String?,
    val appKey: String?,
    val serviceName: String
)
