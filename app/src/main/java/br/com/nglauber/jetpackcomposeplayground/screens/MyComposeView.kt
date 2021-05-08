package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView

class MyComposeView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AbstractComposeView(context, attrs) {

    var viewModel: MyViewModel? = null
        set(value) {
            field = value
            disposeComposition()
        }

    @Composable
    override fun Content() {
        MyComposableWithViewModel(viewModel)
    }
}