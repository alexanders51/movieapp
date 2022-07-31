package com.practica.movieapp.ui.actors

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ActorsActivityContract : ActivityResultContract<Void?, ActorsReturnData?>() {
    override fun createIntent(context: Context, input: Void?): Intent =
        Intent(context, ActorsActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): ActorsReturnData? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra(ActorsActivity.GENRES_RETURN_DATA)
    }
}