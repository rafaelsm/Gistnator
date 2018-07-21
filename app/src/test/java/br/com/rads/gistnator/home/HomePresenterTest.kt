package br.com.rads.gistnator.home

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.gist.response.GistsResponse
import br.com.rads.gistnator.rx.ScheduleProvider
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomePresenterTest {

    private lateinit var homePresenter: HomePresenter
    private val view = mock<HomeContract.View>()
    private val service = mock<GistServiceApi>()
    private val schedulerProvider = mock<ScheduleProvider>()

    @Before
    fun setUp() {
        whenever(schedulerProvider.io()).thenReturn(TestScheduler())
        whenever(schedulerProvider.ui()).thenReturn(TestScheduler())
        homePresenter = HomePresenter(service, schedulerProvider)
        homePresenter.attachView(view)
    }

    @Test
    fun gistsReturnedError() {
        whenever(service.listGists()).thenReturn(Observable.error(Throwable("error mocked")))
        homePresenter.loadGists()
        verify(service, times(1)).listGists()
        verify(view, times(1)).showToastErrorLoadingGists()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, service)
    }
}