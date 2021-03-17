package br.com.nglauber.jetpackcomposeplayground.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import br.com.nglauber.jetpackcomposeplayground.ResponseActivity
import br.com.nglauber.jetpackcomposeplayground.ResponseActivity.Companion.EXTRA_INT
import br.com.nglauber.jetpackcomposeplayground.ResponseActivity.Companion.EXTRA_STRING
import kotlin.random.Random


class MyActivityResultContract : ActivityResultContract<Int, String>() {
    override fun createIntent(context: Context, input: Int?): Intent {
        return Intent(context, ResponseActivity::class.java).apply {
            putExtra(EXTRA_INT, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return if (resultCode == Activity.RESULT_OK) {
            return intent?.getStringExtra(EXTRA_STRING) ?: "Empty result"
        } else {
            "Nothing selected"
        }
    }
}

@Composable
fun GetActivityResultScreen() {
    var textResult by rememberSaveable { mutableStateOf("") }
    val launcher = registerForActivityResult(MyActivityResultContract()) { text ->
        textResult = text
    }

    Column {
        Button(onClick = {
            launcher.launch(Random.nextInt(0, 100))
        }) {
            Text("Launch Activity")
        }
        Text(textResult)
    }
}