package br.com.rads.gistnator.about

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Before
import org.junit.Test

class AboutPresenterTest {

    private lateinit var presenter: AboutPresenter
    private val view = mock<AboutContract.View>()

    @Before
    fun setUp() {
        presenter = AboutPresenter()
        presenter.attachView(view)
    }

    @Test
    fun logoutSelected() {
        presenter.logoutSelected()
        verify(view, times(1)).logout()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view)
    }
}