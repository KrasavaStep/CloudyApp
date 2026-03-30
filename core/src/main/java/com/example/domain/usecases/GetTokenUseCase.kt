package com.example.domain.usecases

import com.example.domain.models.TokenModel
import com.example.domain.repositories.ITokenRepository

class GetTokenUseCase(val tokenRepository: ITokenRepository) {

    operator fun invoke(serviceName: String): TokenModel = tokenRepository.getToken(serviceName)

}