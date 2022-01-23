package app.beta.pokemon.appdi

import android.content.Context
import androidx.room.Room
import app.beta.pokemon.repository.db.AppDatabase
import app.beta.pokemon.repository.db.favorite.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Pokemon"
        ).build()
    }

    @Provides
    fun providesFavoriteDao(appDatabase: AppDatabase): FavoriteDao{
        return appDatabase.favoriteDao()
    }


}