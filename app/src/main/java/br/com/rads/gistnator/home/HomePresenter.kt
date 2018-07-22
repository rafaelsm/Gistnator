package br.com.rads.gistnator.home

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.rx.ScheduleProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val service: GistServiceApi,
                    private val schedulerProvider: ScheduleProvider) : HomeContract.Presenter {

    private var view: HomeContract.View? = null
private var broke = true
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
                            if (broke){
                                broke = false
                                view?.hideMainProgress()
                                view?.showErrorLoadingGists()
                                return@subscribe
                            }

                            val gists = it.map {
                                val fileObj = ((it.files as Map<*, *>).toMap().values.first() as Map<*, *>)
                                Gist(it.owner.login,
                                        fileObj["filename"].toString(),
                                        fileObj["language"].toString(),
                                        fileObj["raw_url"].toString(),
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

}