package com.uesurei.theiconicapp.network.di

import android.content.Context
import com.uesurei.theiconicapp.presentation.BaseApplication
import com.uesurei.theiconicapp.network.api.TheIconicService
import com.uesurei.theiconicapp.network.data.TheIconicRepository
import com.uesurei.theiconicapp.network.db.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideTheIconicRepository(@ApplicationContext context: Context): TheIconicRepository {
        return TheIconicRepository(TheIconicService.create(), ProductDatabase.getInstance(context))
    }
}