package br.com.rads.gistnator.detail

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.rads.gistnator.*
import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.rawfile.RawFileActivity
import io.appflate.restmock.RESTMockServer
import io.realm.Realm
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailActivityTest {

    val GIST = Gist("1", "owner", "name", "language", "raw", "avatar")

    @get:Rule
    var activityTestRule = IntentsTestRule(DetailActivity::class.java, true, false)

    @Before
    fun setUp() {
        Realm.getDefaultInstance().executeTransaction {
            it.deleteAll()
        }
        UrlProvider.baseUrl = RESTMockServer.getUrl()
        RESTMockServer.reset()
    }

    @Test
    fun favoriteGist() {
        activityTestRule.launchActivity(Intent().putExtra(GIST_EXTRA, GIST))
        onView(withId(R.id.gist_owner_textView)).check(matches(withText("owner")))
        onView(withId(R.id.gist_name_textView)).check(matches(withText("name")))
        onView(withId(R.id.gist_language_textView)).check(matches(withText("language")))
        onView(withId(R.id.menu_favorite))
                .check(matches(DrawableMatcher.withTextViewDrawable(R.drawable.ic_favorite_border_black_24dp)))
                .perform(click())
                .check(matches(DrawableMatcher.withTextViewDrawable(R.drawable.ic_favorite_black_24dp)))
    }

    @Test
    fun revertFavoriteGist() {
        favoriteGist()
        onView(withId(R.id.menu_favorite))
                .perform(click())
                .check(matches(DrawableMatcher.withTextViewDrawable(R.drawable.ic_favorite_border_black_24dp)))
    }

    @Test
    fun openRawFile() {
        activityTestRule.launchActivity(Intent().putExtra(GIST_EXTRA, GIST))
        onView(withId(R.id.show_raw_file_button)).perform(click())
        intended(allOf(hasExtra(RAW_FILE_URL_EXTRA, "raw")))
    }
}