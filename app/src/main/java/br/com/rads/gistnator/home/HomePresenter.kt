package br.com.rads.gistnator.home

import android.util.Log
import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.gist.response.GistsResponse
import br.com.rads.gistnator.rx.ScheduleProvider

class HomePresenter(private val service: GistServiceApi,
                    private val schedulerProvider: ScheduleProvider) : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private var loadingMoreGists = false
    private var index = 0

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadGists() {
        view?.hideErrorLoadingGists()
        view?.showMainProgress()
        service.listGists()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        {
                            view?.hideMainProgress()
                            view?.addGistsToList(responseToGist(it))
                        },
                        {
                            view?.hideMainProgress()
                            view?.showErrorLoadingGists()
                        })
    }

    private fun responseToGist(it: List<GistsResponse>): List<Gist> {
        return it.map {
            Gist(it.owner.login,
                    it.files.entries.first().key,
                    it.files.values.first().language ?: "-",
                    it.files.values.first().raw_url,
                    it.owner.avatar_url)
        }
    }

    override fun gistSelected(gist: Gist) {
        view?.openGist(gist)
    }

    override fun isLoading() = loadingMoreGists

    override fun loadMoreGists() {
        if (loadingMoreGists.not()) {
            loadingMoreGists = true
            index++
            service.listGists(page = index, paginateSize = 5)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                            {
                                view?.addGistsToList(responseToGist(it))
                            },
                            {
                                Log.e("Teste", "ruim paginacao")
                                index--
                            },
                            {
                                Log.e("Teste", "complete")
                                loadingMoreGists = false
                            })
        }
    }
}