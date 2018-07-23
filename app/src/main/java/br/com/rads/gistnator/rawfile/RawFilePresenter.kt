package br.com.rads.gistnator.rawfile

import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.rx.ScheduleProvider

class RawFilePresenter(private val serviceApi: GistServiceApi,
                       private val scheduleProvider: ScheduleProvider) : RawFileContract.Presenter {

    private var view: RawFileContract.View? = null

    override fun attachView(view: RawFileContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadRawFile(url: String) {
        view?.showProgress()
        view?.hideErrorLayout()
        serviceApi.rawGist(url)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe(
                        {
                            view?.hideProgress()
                            view?.showRawFile(it.string())
                        },
                        {
                            view?.hideProgress()
                            view?.showErrorLayout()
                        })
    }
}