package br.com.nglauber.jetpackcomposeplayground.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import br.com.nglauber.jetpackcomposeplayground.JetpackComposePlaygroundApp
import java.util.*

object LocaleHelper {
    const val LANGUAGE_EN = "en"
    const val LANGUAGE_PT = "pt"

    fun setLocale(context: Context, language: String): Context? {
        JetpackComposePlaygroundApp.appLanguage = language
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language)
        }
        return updateResourcesLegacy(context, language)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        configuration.uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}