package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.pinch
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import br.com.nglauber.jetpackcomposeplayground.utils.graphicsLayerRotation
import br.com.nglauber.jetpackcomposeplayground.utils.graphicsLayerScale
import br.com.nglauber.jetpackcomposeplayground.utils.hasDrawable
import coil.annotation.ExperimentalCoilApi
import org.junit.Test

@ExperimentalCoilApi
class ImageScreenTest : BaseTest() {

    @Test
    fun images_are_displayed() {
        startApp {
            ImageScreen()
        }

        composeTestRule.onNode(hasDrawable(R.drawable.dog)).assertIsDisplayed()
        composeTestRule.onNode(hasDrawable(R.drawable.img_ba)).assertIsDisplayed()
        composeTestRule.onNode(hasDrawable(R.drawable.balao)).assertIsDisplayed()
        composeTestRule.onNode(hasDrawable(R.mipmap.ic_launcher)).assertIsDisplayed()
        composeTestRule.waitForIdle()

        //TODO Test Coil image is loaded
    }

    @Test
    fun test_image_zoom() {
        startApp {
            ImageScreen()
        }
        composeTestRule.onNodeWithTag(ImageScreenZoomableContainerTestTag)
            .performTouchInput {
                val gap = 16 * density
                val pinchSize = 32 * density
                //TODO is there a better way to do these moves?
                pinch(
                    Offset(width / 2f - gap, height / 2f - gap),
                    Offset(width / 2f - gap - pinchSize, height / 2f - gap - pinchSize),
                    Offset(width / 2f + gap, height / 2f + gap),
                    Offset(width / 2f + gap + pinchSize, height / 2f + gap + pinchSize)
                )
            }
        composeTestRule.waitForIdle()
        // This value was obtained setting the pinchSize and checking the output
        // of the scale.value in the ZoomableImage function in ImageScreen.
        val expectedScale = 2.419353f
        composeTestRule.onNode(graphicsLayerScale(expectedScale)).assertIsDisplayed()
    }

    @Test
    fun test_image_rotation() {
        startApp {
            ImageScreen()
        }
        composeTestRule.onNodeWithTag(ImageScreenZoomableContainerTestTag)
            //TODO is there a better way to do these moves?
            .performTouchInput {
                down(0, Offset(width / 2f, 20 * density))
                down(1, Offset(width / 2f, height / 2f))
                moveTo(0, Offset(20 * density, height / 2f))
            }
        composeTestRule.waitForIdle()
        // This value was obtained checking the output of the
        // rotationState.value in the ZoomableImage function in ImageScreen.
        val expectedRotation = -89.0f
        composeTestRule.onNode(graphicsLayerRotation(expectedRotation)).assertIsDisplayed()
    }
}