package com.practica.movieapp.ui.genres

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class GenresActivityContract : ActivityResultContract<Void?, GenresReturnData?>() {
    override fun createIntent(context: Context, input: Void?): Intent =
        Intent(context, GenresActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): GenresReturnData? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra(GenresActivity.GENRES_RETURN_DATA)
    }
}