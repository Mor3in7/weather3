package androidlead.weatherappui.ui.di

import android.content.Context
import androidlead.weatherappui.ui.auth.SessionDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSessionDataStore(@ApplicationContext context: Context): SessionDataStore =
        SessionDataStore(context)
}
