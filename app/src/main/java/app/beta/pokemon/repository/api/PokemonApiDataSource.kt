package app.beta.pokemon.repository.api

import app.beta.pokemon.GetPokemonQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonApiDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    fun getPokemon(): Flow<ApolloResponse<GetPokemonQuery.Data>> = flow {
        emit(apolloClient.query(GetPokemonQuery()).execute())
    }


}