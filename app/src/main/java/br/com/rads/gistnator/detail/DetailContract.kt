package br.com.rads.gistnator.detail

import br.com.rads.gistnator.gist.Gist

interface DetailContract {

    interface View {
        fun openRawFile(rawFileUrl: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun showRawFileSelected()
        fun addToFavorites(gist: Gist)
        fun removeFromFavorites(gist: Gist)
    }
}