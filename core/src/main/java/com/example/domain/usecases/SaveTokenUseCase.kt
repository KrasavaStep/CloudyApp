package com.example.domain.usecases

import com.example.domain.models.TokenModel
import com.example.domain.repositories.ITokenRepository

class SaveTokenUseCase(val tokenRepository: ITokenRepository) {

    operator fun invoke(token: TokenModel) {
        tokenRepository.saveToken(token)
    }

}