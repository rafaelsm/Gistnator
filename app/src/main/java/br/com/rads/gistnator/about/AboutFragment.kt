package br.com.rads.gistnator.about


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.rads.gistnator.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment(), AboutContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = AboutPresenter()
        presenter.attachView(this)
        logout_button.setOnClickListener {
            presenter.logoutSelected()
        }
    }

    //region AboutContract.View
    override fun logout() {
        activity?.finish()
    }
    //endregion

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }
}
