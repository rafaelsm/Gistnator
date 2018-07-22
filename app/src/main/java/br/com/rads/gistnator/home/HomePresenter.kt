package br.com.rads.gistnator.home

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.rx.ScheduleProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val service: GistServiceApi,
                    private val schedulerProvider: ScheduleProvider) : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private var loadingMoreGists = false

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

                            val gists = it.map {
                                Gist(it.owner.login,
                                        it.files.entries.first().key,
                                        it.files.values.first().language ?: "-",
                                        it.files.values.first().raw_url,
                                        it.owner.avatar_url)
                            }

                            view?.hideMainProgress()
                            view?.addGistsToList(gists)

                        },
                        {
                            view?.hideMainProgress()
                            view?.showErrorLoadingGists()
                        })
    }

    override fun gistSelected(gist: Gist) {
        view?.openGist(gist)
    }

    override fun isLoading() = loadingMoreGists

    override fun loadMoreGists() {
        loadingMoreGists = true
    }
}