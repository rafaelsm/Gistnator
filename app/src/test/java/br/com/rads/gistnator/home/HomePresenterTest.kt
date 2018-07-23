package br.com.rads.gistnator.home

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.gist.response.FileContent
import br.com.rads.gistnator.gist.response.GistsResponse
import br.com.rads.gistnator.gist.response.Owner
import br.com.rads.gistnator.rx.ScheduleProvider
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.internal.stubbing.answers.AnswersWithDelay
import org.mockito.internal.stubbing.answers.Returns

class HomePresenterTest {

    private val GIST = Gist("123", "owner", "name", "lang", "raw", "avatar")

    private lateinit var homePresenter: HomePresenter
    private val view = mock<HomeContract.View>()
    private val service = mock<GistServiceApi>()
    private val schedulerProvider = mock<ScheduleProvider>()

    @Before
    fun setUp() {
        `when`(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        `when`(schedulerProvider.ui()).thenReturn(Schedulers.trampoline())
        homePresenter = HomePresenter(service, schedulerProvider)
        homePresenter.attachView(view)
    }

    @Test
    fun gistsReturnedError() {
        `when`(service.listGists()).thenReturn(Observable.error(Throwable("error mocked")))
        homePresenter.loadGists()
        verify(service, times(1)).listGists()
        verify(view, times(1)).hideErrorLoadingGists()
        verify(view, times(1)).showMainProgress()
        verify(view, times(1)).hideMainProgress()
        verify(view, times(1)).showErrorLoadingGists()
    }

    @Test
    fun gistsReturnedList() {
        `when`(service.listGists()).thenReturn(Observable.just(mockGistList()))
        homePresenter.loadGists()
        verify(service, times(1)).listGists()
        verify(view, times(1)).hideErrorLoadingGists()
        verify(view, times(1)).showMainProgress()
        verify(view, times(1)).hideMainProgress()
        verify(view, times(1)).addGistsToList(any())
    }

    @Test
    fun gistsReturnedEmptyList() {
        `when`(service.listGists()).thenReturn(Observable.just(listOf()))
        homePresenter.loadGists()
        verify(service, times(1)).listGists()
        verify(view, times(1)).hideErrorLoadingGists()
        verify(view, times(1)).showMainProgress()
        verify(view, times(1)).hideMainProgress()
        verify(view, times(1)).addGistsToList(eq(listOf()))
    }

    @Test
    fun gistSelected() {
        homePresenter.gistSelected(GIST)
        verify(view, times(1)).openGist(GIST)
    }

    @Test
    fun paginateSuccess() {
        `when`(service.listGists(any(), any())).thenReturn(Observable.just(mockGistList()))
        homePresenter.loadMoreGists()
        verify(service, times(1)).listGists(any(), any())
        verify(view, times(1)).addGistsToList(any())
    }

    @Test
    fun paginateFail() {
        `when`(service.listGists(any(), any())).thenReturn(Observable.error(Throwable("error mocked")))
        homePresenter.loadMoreGists()
        verify(service, times(1)).listGists(any(), any())
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, service)
    }

    //region Helper methods
    private fun mockGistList(): List<GistsResponse> {
        return listOf(GistsResponse("", "", "", "", "", "", true,
                Owner("", 1, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", false), "",
                mapOf("files" to FileContent(1, "", "", false, "")),
                true, 1, "", "", "", "", "", ""))
    }
    //endregion
}