package es.josehector.scanid.presentation.mobbscan

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class MobbScanActivityTest {

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
    fun shouldDisplayFrontAndBackButtons() {
        onView(withId(R.id.bt_back)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_back)).check(matches(isClickable()))
        onView(withId(R.id.bt_front)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_front)).check(matches(isClickable()))
    }

    @Test
    fun shouldDisplayCameraAndGallerySelectorButtons(){
        onView(withId(R.id.iv_camera)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_camera)).check(matches(isClickable()))
        onView(withId(R.id.iv_gallery)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_gallery)).check(matches(isClickable()))
    }

    @Test
    fun shouldDisplayCheckButton(){
        onView(withId(R.id.bt_check)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_check)).check(matches(isClickable()))
    }

    @Test
    fun shouldDisplaySelectMessage(){
        onView(withId(R.id.tv_select)).check(matches(isDisplayed()))
    }
}
