package br.com.rads.gistnator.about

class AboutPresenter : AboutContract.Presenter {

    private var view: AboutContract.View? = null

    override fun attachView(view: AboutContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun logoutSelected() {
        view?.logout()
    }
}