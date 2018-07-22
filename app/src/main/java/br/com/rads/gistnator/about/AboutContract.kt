package br.com.rads.gistnator.about

interface AboutContract {
    interface View {
        fun logout()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun logoutSelected()
    }

}