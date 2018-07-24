package br.com.rads.gistnator.rawfile

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.rads.gistnator.MainActivity
import br.com.rads.gistnator.R
import br.com.rads.gistnator.RAW_FILE_URL_EXTRA
import br.com.rads.gistnator.UrlProvider
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import io.appflate.restmock.utils.RequestMatchers.*
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RawFileActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(RawFileActivity::class.java, true, false)

    @Before
    fun setUp() {
        UrlProvider.baseUrl = RESTMockServer.getUrl()
        RESTMockServer.reset()
    }

    @Test
    fun rawFileLoadSuccess() {
        RESTMockServer.whenGET(pathContains("raw")).thenReturnFile(200, "rawfile/ring.erl")
        launchActivityWithRawUrl()
        onView(withId(R.id.raw_file_textView))
                .check(matches(isDisplayed()))
                .check(matches(withText(containsString("statistics(wall_clock)"))))
    }

    private fun launchActivityWithRawUrl() {
        activityTestRule.launchActivity(Intent().putExtra(RAW_FILE_URL_EXTRA, UrlProvider.baseUrl + "raw/365370/8c4d2d43d178df44f4c03a7f2ac0ff512853564e/ring.erl"))
    }

    @Test
    fun rawFileLoadFailed() {
        RESTMockServer.whenGET(pathContains("raw")).thenReturnEmpty(500)
        launchActivityWithRawUrl()
        onView(withId(R.id.raw_file_textView)).check(matches(withText(isEmptyString())))
        onView(withId(R.id.error_linearLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun retryShouldLoadRawFile() {
        RESTMockServer.whenGET(pathContains("raw"))
                .thenReturnEmpty(500)
                .thenReturnFile(200, "rawfile/ring.erl")
        launchActivityWithRawUrl()
        onView(withId(R.id.raw_file_textView)).check(matches(withText(isEmptyString())))
        onView(withId(R.id.error_linearLayout)).check(matches(isDisplayed()))

        onView(withId(R.id.try_again_button)).perform(click())

        onView(withId(R.id.error_linearLayout)).check(matches(not(isDisplayed())))
        onView(withId(R.id.raw_file_textView))
                .check(matches(isDisplayed()))
                .check(matches(withText(containsString("statistics(wall_clock)"))))

    }
}