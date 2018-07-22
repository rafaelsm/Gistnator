package br.com.rads.gistnator.detail

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.datasource.GistDataSource

class DetailPresenter(val gist: Gist, val gistDataSource: GistDataSource) : DetailContract.Presenter {

    private var view: DetailContract.View? = null

    override fun attachView(view: DetailContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun showRawFileSelected() {
        view?.openRawFile(gist.rawUrl)
    }

    override fun addToFavorites() {
        gistDataSource.save(gist)
        view?.gistAddedAsFavorites()
    }

    override fun removeFromFavorites() {
        gistDataSource.delete(gist)
        view?.gistRemovedFromFavorites()
    }

    override fun gistIsFavorite(): Boolean {
        return gistDataSource.contains(gist)
    }
}