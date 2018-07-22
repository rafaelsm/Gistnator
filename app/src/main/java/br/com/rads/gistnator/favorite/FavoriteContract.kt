package br.com.rads.gistnator.favorite

import br.com.rads.gistnator.gist.Gist

interface FavoriteContract {

    interface View {
        fun showGists(gists: List<Gist>)
        fun gistRemoved(gist: Gist)
        fun openGist(gist: Gist)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadFavoriteGists()
        fun removeFromFavorite(gist: Gist)
        fun gistSelected(gist: Gist)
    }
}