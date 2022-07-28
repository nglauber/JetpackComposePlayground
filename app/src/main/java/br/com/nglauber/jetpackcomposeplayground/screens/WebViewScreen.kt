package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.webkit.*
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewFeature
import br.com.nglauber.jetpackcomposeplayground.util.ConfigurationUtil

@Composable
fun WebViewScreen() {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        factory = { context ->
            object : WebView(context) {
                init {
                    // https://developer.android.com/reference/androidx/webkit/WebViewAssetLoader
                    val assetLoader = WebViewAssetLoader.Builder()
                        .addPathHandler("/res/", WebViewAssetLoader.ResourcesPathHandler(context))
                        .build()

                    webChromeClient = WebChromeClient()
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView,
                            request: WebResourceRequest
                        ): Boolean {
                            return false
                        }

                        override fun shouldInterceptRequest(
                            view: WebView,
                            request: WebResourceRequest
                        ): WebResourceResponse? {
                            return assetLoader.shouldInterceptRequest(request.url)
                        }
                    }
                    setBackgroundColor(0)
                    val configuration = ConfigurationUtil.getConfiguration(context)
                    if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                        when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                            Configuration.UI_MODE_NIGHT_YES -> {
                                WebSettingsCompat.setForceDark(
                                    settings,
                                    WebSettingsCompat.FORCE_DARK_ON
                                )
                            }
                            Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                                WebSettingsCompat.setForceDark(
                                    settings,
                                    WebSettingsCompat.FORCE_DARK_OFF
                                )
                            }
                            else -> {
                                WebSettingsCompat.setForceDark(
                                    settings,
                                    WebSettingsCompat.FORCE_DARK_AUTO
                                )
                            }
                        }
                    }
                    settings.allowFileAccess = false
                    settings.allowContentAccess = false
                    setDownloadListener { url, _, _, _, _ ->
                        url?.let {
                            try {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW).apply {
                                        data = Uri.parse(it)
                                    }
                                )
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error opening link", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                    loadDataWithBaseURL(
                        ResourceBaseUrl,
                        getStyledHtmlContent("This is a web view. Check if it is working on Dark Mode"),
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            }
        }
    )
}

private fun getStyledHtmlContent(content: String) =
    """
    <html>
    <head>
        <style>
            @font-face {
                font-family: 'NEXTPanBook';
                src: url('$ResourceBaseUrl/res/font/oswald_regular.ttf');
            }
            body {
                font-family: Oswald;
                line-height: 1.5;
                text-align: justify;
            }
        </style>
    </head>
    <body>$content</body>
    </html>
    """.trimIndent()

private const val ResourceBaseUrl = "https://appassets.androidplatform.net"