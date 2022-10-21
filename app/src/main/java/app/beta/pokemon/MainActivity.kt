package app.beta.pokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.beta.pokemon.databinding.MainActivityBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var instance: AppCompatActivity
            private set
    }

    private lateinit var viewBinding: MainActivityBinding

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportActionBar?.hide()
        instance = this

        analytics = FirebaseAnalytics.getInstance(baseContext)
    }
}