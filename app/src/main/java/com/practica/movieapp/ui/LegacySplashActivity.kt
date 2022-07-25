package com.practica.movieapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.practica.movieapp.R
import java.util.*

private const val ARRAY_MAXSZ: Int = 9
private const val ARRAY_RESNAME: String = "tv_splash_text_rand_"
private const val ARRAY_RESDEF: String = "string"
private const val SPLASH_TIMEOUT: Long = 1000L

@SuppressLint("CustomSplashScreen")
class LegacySplashActivity : AppCompatActivity() {
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private lateinit var randomStrings: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legacy_splash)

        loadRandomStringsArray()
        setRandomString()

        initHandlerToOpenNextActivity()
    }

    private fun initHandlerToOpenNextActivity() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            openNextScreen()
        }

        handler?.postDelayed(runnable!!, SPLASH_TIMEOUT)
    }

    private fun openNextScreen() {
        OnboardingScreenActivity.open(this)
        finish()
    }

    override fun onDestroy() {
        removeHandler()
        super.onDestroy()
    }

    override fun onBackPressed() {
        removeHandler()
        super.onBackPressed()
    }

    private fun removeHandler() {
        if (handler != null && runnable != null) {
            handler?.removeCallbacks(runnable!!)
            runnable = null
            handler = null
        }
    }

    private fun loadRandomStringsArray() {
        randomStrings = Array(ARRAY_MAXSZ) { it }
        for (i in 0 until ARRAY_MAXSZ) {
            val resName: String = ARRAY_RESNAME + (i + 1).toString()
            randomStrings[i] = resources.getIdentifier(resName, ARRAY_RESDEF, packageName)
        }
    }

    private fun setRandomString() {
        val tvSplashText = findViewById<TextView>(R.id.tvSplashText)
        val rand = Random(System.currentTimeMillis())
        tvSplashText.text = getString(randomStrings[rand.nextInt(ARRAY_MAXSZ)])
    }
}