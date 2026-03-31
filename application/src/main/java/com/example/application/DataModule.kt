package com.example.application

import android.content.Context
import com.example.common.EncryptedTokenRepositoryImpl
import com.example.domain.repositories.IFilesRepository
import com.example.domain.repositories.ITokenRepository
import com.example.domain.usecases.GetFilesUseCase
import com.example.domain.usecases.GetTokenUseCase
import com.example.domain.usecases.SaveFilesUseCase
import com.example.domain.usecases.SaveTokenUseCase
import com.example.dropbox.DropBoxFileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTokenRepository(@ApplicationContext context: Context): ITokenRepository {
        return EncryptedTokenRepositoryImpl(context)
    }

    @Provides
    fun provideGetTokenUseCase(tokenRepository: ITokenRepository): GetTokenUseCase {
        return GetTokenUseCase(tokenRepository)
    }

    @Provides
    fun provideSaveTokenUseCase(tokenRepository: ITokenRepository): SaveTokenUseCase {
        return SaveTokenUseCase(tokenRepository)
    }

    @Provides
    @Singleton
    @Named("Dropbox")
    fun provideDropBoxRepository(getTokenUseCase: GetTokenUseCase): IFilesRepository {
        return DropBoxFileRepositoryImpl(getTokenUseCase)
    }

    @Provides
    @Named("GetDropbox")
    fun provideGetFilesUseCase(@Named("Dropbox") filesRepository: IFilesRepository): GetFilesUseCase {
        return GetFilesUseCase(filesRepository)
    }

    @Provides
    @Named("SaveDropbox")
    fun provideSaveFilesUseCase(@Named("Dropbox") filesRepository: IFilesRepository): SaveFilesUseCase {
        return SaveFilesUseCase(filesRepository)
    }


}