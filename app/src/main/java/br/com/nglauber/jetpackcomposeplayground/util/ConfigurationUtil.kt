package br.com.nglauber.jetpackcomposeplayground.util

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

object ConfigurationUtil {

    fun getConfiguration(context: Context): Configuration {
        /**
         * issue tracker :  https://issuetracker.google.com/issues/170328697
         *  Web view resets uiMode (Day/Night) in Configuration
         */
        val configuration = context.resources.configuration
        val configurationNighMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val appCompatNightMode = AppCompatDelegate.getDefaultNightMode()

        val newUiModeConfiguration = when {
            configurationNighMode == Configuration.UI_MODE_NIGHT_NO && appCompatNightMode == UiModeManager.MODE_NIGHT_YES -> {
                Configuration.UI_MODE_NIGHT_YES or (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv())
            }
            configurationNighMode == Configuration.UI_MODE_NIGHT_YES && appCompatNightMode == UiModeManager.MODE_NIGHT_NO -> {
                Configuration.UI_MODE_NIGHT_NO or (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv())
            }
            else -> null
        }

        if (newUiModeConfiguration != null) {
            val fixedConfiguration = Configuration().apply {
                configuration.uiMode = newUiModeConfiguration
            }
            context.createConfigurationContext(fixedConfiguration)
        }
        return configuration
    }
}