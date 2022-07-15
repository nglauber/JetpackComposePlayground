package br.com.nglauber.jetpackcomposeplayground.screens

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import br.com.nglauber.jetpackcomposeplayground.JetpackComposePlaygroundApp
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.util.LocaleHelper

@Composable
fun ChangeLanguageScreen() {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.msg_change_language))
        Button(onClick = {
            if (JetpackComposePlaygroundApp.appLanguage == LocaleHelper.LANGUAGE_PT)
                LocaleHelper.setLocale(context, LocaleHelper.LANGUAGE_EN)
            else
                LocaleHelper.setLocale(context, LocaleHelper.LANGUAGE_PT)
            (context as? Activity)?.recreate()
        }) {
            Text(text = stringResource(id = R.string.btn_ok))
        }
    }
}