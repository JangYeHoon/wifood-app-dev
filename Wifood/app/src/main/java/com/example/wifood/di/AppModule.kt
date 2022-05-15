package com.example.wifood.di

import com.example.wifood.data.remote.WifoodApi
import com.example.wifood.data.remote.WifoodApiImpl
import com.example.wifood.domain.usecase.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideUseCases(): WifoodUseCases {
        return WifoodUseCases(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRepeatedPassword = ValidateRepeatedPassword(),
            validateTerms = ValidateTerms()
        )
    }

    @Provides
    @Singleton
    fun provideApi(db: DatabaseReference): WifoodApi {
        return WifoodApiImpl(db)
    }
}