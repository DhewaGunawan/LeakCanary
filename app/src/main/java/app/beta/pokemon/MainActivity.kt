package app.beta.pokemon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import app.beta.pokemon.ui.main.MainFragment
import com.apollographql.apollo3.ApolloClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .build()

        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(GetPokemonQuery()).execute()

            Log.d("LaunchList", "Success ${response.data}")
        }
    }
}