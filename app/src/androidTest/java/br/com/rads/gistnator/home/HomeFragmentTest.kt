package br.com.rads.gistnator.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import br.com.rads.gistnator.MainActivity
import br.com.rads.gistnator.R
import br.com.rads.gistnator.UrlProvider
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        UrlProvider.baseUrl = RESTMockServer.getUrl()
        RESTMockServer.reset()
    }

    @Test
    fun loadingListSuccess() {
        RESTMockServer.whenGET(RequestMatchers.pathContains("gists")).thenReturnFile(200, "home/gist_list_success.json")
        activityTestRule.launchActivity(null)
        onView(withId(R.id.error_linearLayout)).check(matches(not(isDisplayed())))
        onView(withText("octocat")).check(matches(isDisplayed()))
    }

    @Test
    fun loadingListFail() {
        RESTMockServer.whenGET(RequestMatchers.pathContains("gists")).thenReturnEmpty(500)
        activityTestRule.launchActivity(null)
        onView(withId(R.id.error_linearLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun retryWorking() {
        RESTMockServer.whenGET(RequestMatchers.pathContains("gists")).thenReturnFile(200, "home/gist_list_success.json")
        activityTestRule.launchActivity(null)
        onView(withId(R.id.home_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.show_raw_file_button)).check(matches(isDisplayed()))
    }
}