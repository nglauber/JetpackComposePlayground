package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.test.*
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Test

@ExperimentalPagerApi
class ViewPagerTabsScreenTest : BaseTest() {

    @Test
    fun swipe_between_pages() {
        startApp {
            ViewPagerTabsScreen()
        }
        val numTabs = 5
        (1..numTabs).forEach {
            composeTestRule.onNode(hasText("Tab $it")).assertIsSelected()
            if (it < numTabs) {
                composeTestRule.onNode(hasAnyChild(hasText("Page $it"))).performTouchInput {
                    swipeLeft()
                }
            }
        }
        (numTabs downTo 1).forEach {
            composeTestRule.onNode(hasText("Tab $it")).assertIsSelected()
            if (it > 1) {
                composeTestRule.onNode(hasAnyChild(hasText("Page $it"))).performTouchInput {
                    swipeRight()
                }
            }
        }
    }
}