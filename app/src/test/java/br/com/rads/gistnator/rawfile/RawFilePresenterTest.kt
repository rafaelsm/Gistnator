package br.com.rads.gistnator.rawfile

import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.rx.ScheduleProvider
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RawFilePresenterTest {

    private val MOCK_URL = "mock_url"
    private val MOCK_CONTENT = "mock_content"
    private lateinit var presenter: RawFilePresenter
    private val view = mock<RawFileContract.View>()
    private val service = mock<GistServiceApi>()
    private val schedulerProvider = mock<ScheduleProvider>()

    @Before
    fun setUp() {
        Mockito.`when`(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulerProvider.ui()).thenReturn(Schedulers.trampoline())
        presenter = RawFilePresenter(service, schedulerProvider)
        presenter.attachView(view)
    }

    @Test
    fun loadRawFileSuccess() {
        Mockito.`when`(service.rawGist(any())).thenReturn(Observable.just(ResponseBody.create(MediaType.parse("text"), MOCK_CONTENT)))
        presenter.loadRawFile(MOCK_URL)
        verify(service, times(1)).rawGist(MOCK_URL)
        verify(view, times(1)).hideErrorLayout()
        verify(view, times(1)).showProgress()
        verify(view, times(1)).hideProgress()
        verify(view, times(1)).showRawFile(MOCK_CONTENT)
    }

    @Test
    fun loadRawFileFail() {
        Mockito.`when`(service.rawGist(any())).thenReturn(Observable.error(Throwable("error")))
        presenter.loadRawFile(MOCK_URL)
        verify(service, times(1)).rawGist(MOCK_URL)
        verify(view, times(1)).hideErrorLayout()
        verify(view, times(1)).showProgress()
        verify(view, times(1)).showErrorLayout()
        verify(view, times(1)).hideProgress()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, service)
    }
}