package br.com.rads.gistnator.home

import br.com.rads.gistnator.gist.Gist

interface HomeContract {

    interface View {
        fun showMainProgress()
        fun hideMainProgress()
        fun showErrorLoadingGists()
        fun hideErrorLoadingGists()
        fun showToastErrorLoadingGists()
        fun addGistsToList(gists: List<Gist>)
        fun openGist(gist: Gist)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadGists()
        fun loadMoreGists()
        fun gistSelected(gist: Gist)
        fun isLoading(): Boolean
    }
}