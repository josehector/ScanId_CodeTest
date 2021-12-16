package es.josehector.scanid.presentation.mobbscan

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import es.josehector.scanid.R
import es.josehector.scanid.presentation.mobbScan.MobbScanActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MobbScanActivityTest: ScreenshotTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule: ActivityScenarioRule<MobbScanActivity> =
        ActivityScenarioRule(MobbScanActivity::class.java)


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun shouldDisplayMainScreen() {
        compareScreenshot(activityRule.scenario.waitForActivity())
    }

    @Test
    fun shouldDisplayFrontAndBackButtons() {
        Espresso.onView(ViewMatchers.withId(R.id.bt_back))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.bt_back))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
        Espresso.onView(ViewMatchers.withId(R.id.bt_front))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.bt_front))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }

    @Test
    fun shouldDisplayCameraAndGallerySelectorButtons(){
        Espresso.onView(ViewMatchers.withId(R.id.iv_camera))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.iv_camera))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
        Espresso.onView(ViewMatchers.withId(R.id.iv_gallery))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.iv_gallery))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }

    @Test
    fun shouldDisplayCheckButton(){
        Espresso.onView(ViewMatchers.withId(R.id.bt_check))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.bt_check))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }

    @Test
    fun shouldDisplaySelectMessage(){
        Espresso.onView(ViewMatchers.withId(R.id.tv_select))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
