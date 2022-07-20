package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performTouchInput
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import br.com.nglauber.jetpackcomposeplayground.utils.graphicsLayerRotation
import br.com.nglauber.jetpackcomposeplayground.utils.hasDrawable
import org.junit.Test

@ExperimentalComposeUiApi
class ImageRotationScreenTest : BaseTest() {

    @Test
    fun images_rotation_is_working() {
        startApp {
            OneFingerImageRotationScreen()
        }

        composeTestRule.onNode(hasDrawable(R.drawable.dog))
            .performTouchInput {
                // Moving the finger from start/center to center/top
                down(Offset(0f, height / 2f))
                moveTo(Offset(width / 2f, 0f))
                up()
            }
        composeTestRule.onNode(graphicsLayerRotation(90f)).assertIsDisplayed()

        composeTestRule.onNode(hasDrawable(R.drawable.dog))
            .performTouchInput {
                // Moving the finger from center/top to end/center
                down(Offset(width / 2f, 0f))
                moveTo(Offset(width.toFloat(), height / 2f))
                up()
            }

        composeTestRule.onNode(graphicsLayerRotation(180f)).assertIsDisplayed()
    }
}