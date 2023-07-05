package kinomaxi.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kinomaxi.dataStore.DataStoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreRepository =
        DataStoreRepository(context)

}