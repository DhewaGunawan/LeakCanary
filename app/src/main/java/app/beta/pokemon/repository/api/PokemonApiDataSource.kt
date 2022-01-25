package app.beta.pokemon.repository.api

import app.beta.pokemon.GetPokemonDetailQuery
import app.beta.pokemon.GetPokemonQuery
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonApiDataSource @Inject constructor(private val apolloClient: ApolloClient) {



    fun getPokemon(page: Int = 0) = flow {
        val offset = 25 * page
        emit(apolloClient.query(GetPokemonQuery(offset)).execute())
    }

    fun getPokemonDetail(id: Int) = flow {
        emit(apolloClient.query(GetPokemonDetailQuery(id)).execute())
    }

}