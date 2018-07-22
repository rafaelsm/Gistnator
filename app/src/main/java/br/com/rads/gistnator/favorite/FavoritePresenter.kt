package br.com.rads.gistnator.favorite

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.datasource.GistDataSource

class FavoritePresenter(private val dataSource: GistDataSource) : FavoriteContract.Presenter {

    private var view: FavoriteContract.View? = null

    override fun attachView(view: FavoriteContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadFavoriteGists() {
        view?.showGists(dataSource.listGists())
    }

    override fun removeFromFavorite(gist: Gist) {
        dataSource.delete(gist)
        view?.gistRemoved(gist)
    }

    override fun gistSelected(gist: Gist) {
        view?.openGist(gist)
    }
}