package br.com.nglauber.jetpackcomposeplayground

import android.app.Application
import android.content.Context
import br.com.nglauber.jetpackcomposeplayground.util.LocaleHelper

class JetpackComposePlaygroundApp : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(base, appLanguage))
    }

    companion object {
        //TODO save in shared preferences
        var appLanguage = LocaleHelper.LANGUAGE_EN
    }
}