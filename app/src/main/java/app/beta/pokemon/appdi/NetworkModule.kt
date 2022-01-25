package app.beta.pokemon.appdi

import app.beta.pokemon.MainActivity
import app.beta.pokemon.R
import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(MainActivity.instance.getString(R.string.api_url))
            .build()
    }

}