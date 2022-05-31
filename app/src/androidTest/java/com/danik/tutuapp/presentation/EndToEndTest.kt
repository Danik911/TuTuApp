package com.danik.tutuapp.presentation

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.platform.app.InstrumentationRegistry
import com.danik.tutuapp.MainActivity
import com.danik.tutuapp.di.RepositoryModule
import com.danik.tutuapp.navigation.Screen
import com.danik.tutuapp.presentation.screens.details.DetailsScreen
import com.danik.tutuapp.presentation.screens.main.MainScreen
import com.danik.tutuapp.ui.theme.TuTuAppTheme
import com.danik.tutuapp.util.Constants
import com.danik.tutuapp.util.TestTags.LAZY_COLUMN
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.danik.tutuapp.R
import com.danik.tutuapp.util.TestTags


@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class EndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    private val contextTestRule = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent {
            TuTuAppTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                ) {

                    composable(Screen.Main.route) {
                        MainScreen(navHostController = navController)
                    }
                    composable(
                        Screen.Detail.route,
                        arguments = listOf(
                            navArgument(
                                Constants.TRAIN_ID_ARGUMENT_KEY
                            ) {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        DetailsScreen(navHostController = navController, testMode = true)
                    }
                }
            }
        }
    }


    @Test
    fun scrollList_clickOnTrain_navigateToDetailScreen_clickCloseIcon() {


        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")

        composeTestRule.onNodeWithTag(LAZY_COLUMN).performTouchInput { swipeUp() }
        composeTestRule.onNodeWithTag(LAZY_COLUMN).performTouchInput { swipeUp() }
        composeTestRule.onNodeWithText("Stefano Lombardo").performClick()

        //Detail screen
        composeTestRule.onNodeWithTag(TestTags.IMAGE).assertExists()
        composeTestRule.onNodeWithContentDescription(contextTestRule.getString(R.string.ic_close))
            .performClick()

        //Main screen
        composeTestRule.onNodeWithTag(LAZY_COLUMN).assertIsDisplayed()


    }

}