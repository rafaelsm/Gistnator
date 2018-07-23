package br.com.rads.gistnator.detail

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.datasource.GistDataSource
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DetailPresenterTest {

    val GIST = Gist("1", "owner", "name", "language", "raw", "avatar")
    private lateinit var presenter: DetailPresenter
    private val dataSource = mock<GistDataSource>()
    private val view = mock<DetailContract.View>()

    @Before
    fun setUp() {
        presenter = DetailPresenter(GIST, dataSource)
        presenter.attachView(view)
    }

    @Test
    fun isAddingToFavorite() {
        presenter.addToFavorites()
        verify(dataSource, times(1)).save(GIST)
        verify(view, times(1)).gistAddedAsFavorites()
    }

    @Test
    fun removingFromFavorites() {
        presenter.removeFromFavorites()
        verify(dataSource, times(1)).delete(GIST)
        verify(view, times(1)).gistRemovedFromFavorites()
    }

    @Test
    fun checkingIfGistIsFavorite() {
        whenever(dataSource.contains(GIST)).thenReturn(true)
        presenter.gistIsFavorite()
        verify(dataSource, times(1)).contains(GIST)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(dataSource, view)
    }
}