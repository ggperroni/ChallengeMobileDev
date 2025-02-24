package com.ggperroni.challengemobiledev.di

import android.content.Context
import androidx.room.Room
import com.ggperroni.challengemobiledev.data.local.AppDatabase
import com.ggperroni.challengemobiledev.data.local.dao.TreeItemDao
import com.ggperroni.challengemobiledev.data.local.dao.UserDao
import com.ggperroni.challengemobiledev.data.login.LoginRepository
import com.ggperroni.challengemobiledev.data.login.LoginRepositoryImpl
import com.ggperroni.challengemobiledev.data.remote.ApiService
import com.ggperroni.challengemobiledev.data.tree.TreeRepository
import com.ggperroni.challengemobiledev.data.tree.TreeRepositoryImpl
import com.ggperroni.challengemobiledev.domain.login.LoginUseCase
import com.ggperroni.challengemobiledev.domain.login.LoginUseCaseImpl
import com.ggperroni.challengemobiledev.domain.tree.GetTreeUseCase
import com.ggperroni.challengemobiledev.view.components.AuthInterceptor
import com.ggperroni.challengemobiledev.view.components.SecurePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideLoginRepository(
        userDao: UserDao,
        apiService: ApiService,
        securePreferences: SecurePreferences
    ): LoginRepository {
        return LoginRepositoryImpl(apiService, securePreferences, userDao)
    }

    @Provides
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://apitestemobile-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideSecurePreferences(@ApplicationContext context: Context): SecurePreferences {
        return SecurePreferences(context)
    }

    @Provides
    fun provideTreeRepository(
        api: ApiService,
        dao: TreeItemDao,
        prefs: SecurePreferences
    ): TreeRepository = TreeRepositoryImpl(api, dao, prefs)

    @Provides
    fun provideGetTreeUseCase(
        repository: TreeRepository
    ): GetTreeUseCase = GetTreeUseCase(repository)

    @Provides
    fun provideTreeItemDao(database: AppDatabase): TreeItemDao {
        return database.treeItemDao()
    }
}