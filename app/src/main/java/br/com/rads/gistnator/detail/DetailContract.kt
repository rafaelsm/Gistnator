package br.com.rads.gistnator.detail

interface DetailContract {

    interface View {
        fun openRawFile(rawFileUrl: String)
        fun gistAddedAsFavorites()
        fun gistRemovedFromFavorites()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun showRawFileSelected()
        fun addToFavorites()
        fun removeFromFavorites()
        fun gistIsFavorite(): Boolean
    }
}