package br.com.rads.gistnator.detail

import br.com.rads.gistnator.gist.Gist

class DetailPresenter(val gist: Gist) : DetailContract.Presenter {

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

    override fun addToFavorites(gist: Gist) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFromFavorites(gist: Gist) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}