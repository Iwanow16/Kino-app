package kinomaxi.di

import dagger.Module
import dagger.Provides
import kinomaxi.subnavigation.LocalCiceroneHolder
import javax.inject.Singleton

@Module
//@InstallIn(SingletonComponent::class)
object LocalNavigationModule {

    @Provides
    @Singleton
    fun provideLocalNavigationHolder(): LocalCiceroneHolder = LocalCiceroneHolder()
}