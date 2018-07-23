package br.com.rads.gistnator.favorite

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.datasource.GistDataSource
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavoritePresenterTest {

    val GIST = Gist("1", "owner", "name", "language", "raw", "avatar")
    private val GIST_LIST = listOf(GIST)

    private lateinit var presenter: FavoritePresenter
    private var dataSource = mock<GistDataSource>()
    private var view = mock<FavoriteContract.View>()

    @Before
    fun setUp() {
        presenter = FavoritePresenter(dataSource)
        presenter.attachView(view)
    }

    @Test
    fun loadingFavorites() {
        whenever(dataSource.listGists()).thenReturn(GIST_LIST)
        presenter.loadFavoriteGists()
        verify(dataSource, times(1)).listGists()
        verify(view, times(1)).showGists(GIST_LIST)
    }

    @Test
    fun removeFromFavorite() {
        presenter.removeFromFavorite(GIST)
        verify(dataSource, times(1)).delete(GIST)
        verify(view, times(1)).gistRemoved(GIST)
    }

    @Test
    fun selectGist() {
        presenter.gistSelected(GIST)
        verify(view, times(1)).openGist(GIST)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, dataSource)
        presenter.detachView()
    }
}