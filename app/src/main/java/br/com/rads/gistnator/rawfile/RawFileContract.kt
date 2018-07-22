package br.com.rads.gistnator.rawfile

interface RawFileContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showErrorLayout()
        fun hideErrorLayout()
    }

    interface Presenter {
        fun loadRawFile(url: String)
    }

}