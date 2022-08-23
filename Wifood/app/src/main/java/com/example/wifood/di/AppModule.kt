package com.example.wifood.di

import android.app.Application
import androidx.room.Room
import com.example.wifood.data.local.dao.WifoodDatabase
import com.example.wifood.data.remote.WifoodApi
import com.example.wifood.data.remote.WifoodApiImpl
import com.example.wifood.data.repository.WifoodRepositoryImpl
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.domain.usecase.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: WifoodRepository): WifoodUseCases {
        return WifoodUseCases(
            ValidatePhone = ValidatePhone(),
            validateEmail = ValidateEmail(repository),
            validatePassword = ValidatePassword(repository),
            validateRepeatedPassword = ValidateRepeatedPassword(repository),
            validateNickname = ValidateNickname(repository),
            validateBirthday = ValidateBirthday(),
            validateTerms = ValidateTerms(repository),
            GetUserAllData = GetUserAllData(repository),
            InsertUser = InsertUser(repository),
            DeletePlace = DeletePlace(repository),
            GetPlaceImageUris = GetPlaceImageUris(repository),
            DeleteGroup = DeleteGroup(repository),
            InsertGroup = InsertGroup(repository),
            UpdateGroup = UpdateGroup(repository),
            GetGroups = GetGroups(repository),
            InsertPlace = InsertPlace(repository),
            InsertPlaceImages = InsertPlaceImages(repository),
            GetUserInfo = GetUserInfo(repository),
            GetTMapSearchPlaceResult = GetTMapSearchPlaceResult(repository),
            GetTMapSearchAddressResult = GetTMapSearchAddressResult(repository),
            GetPlaceImageUri = GetPlaceImageUri(repository),
            RequestCertNumber = RequestCertNumber(repository)
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }

    @Provides
    @Singleton
    fun provideRepository(db: WifoodDatabase, api: WifoodApi): WifoodRepository {
        return WifoodRepositoryImpl(db.wifoodDao, api)
    }

    @Provides
    @Singleton
    fun provideApi(fb: DatabaseReference, client: HttpClient): WifoodApi {
        return WifoodApiImpl(fb, client)
    }

    @Singleton
    @Provides
    fun provideFirebase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WifoodDatabase {
        return Room.databaseBuilder(
            app,
            WifoodDatabase::class.java,
            WifoodDatabase.DATABASE_NAME
        ).build()
    }
}