package br.com.rads.gistnator.rawfile

interface RawFileContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showErrorLayout()
        fun hideErrorLayout()
        fun showRawFile(raw: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadRawFile(url: String)
    }

}