package br.com.rads.gistnator.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.rads.gistnator.GIST_EXTRA

import br.com.rads.gistnator.R
import br.com.rads.gistnator.detail.DetailActivity
import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.gone
import br.com.rads.gistnator.rx.SchedulerProviderImpl
import br.com.rads.gistnator.visible
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class HomeFragment : Fragment(), HomeContract.View {

    private var homePresenter: HomePresenter? = null
    private var adater: HomeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val gistApi = retrofit.create(GistServiceApi::class.java)
        homePresenter = HomePresenter(gistApi, SchedulerProviderImpl())

        adater = HomeAdapter(mutableListOf()) { homePresenter?.gistSelected(it) }
        home_recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        home_recyclerView.adapter = adater

        try_again_button.setOnClickListener {
            homePresenter?.loadGists()
        }
    }

    override fun onStart() {
        super.onStart()
        homePresenter?.attachView(this)
        homePresenter?.loadGists()
    }

    override fun onStop() {
        super.onStop()
        homePresenter?.detachView()
    }

    //region HomeContract.View
    override fun showMainProgress() {
        main_progressBar?.visible()
    }

    override fun hideMainProgress() {
        main_progressBar?.gone()
    }

    override fun showErrorLoadingGists() {
        error_linearLayout?.visible()
    }

    override fun hideErrorLoadingGists() {
        error_linearLayout?.gone()
    }

    override fun showToastErrorLoadingGists() =
            Toast.makeText(context, "Error loading gists", Toast.LENGTH_SHORT).show()

    override fun addGistsToList(gists: List<Gist>) {
        home_recyclerView.visibility = View.VISIBLE
        adater?.addAll(gists)
    }

    override fun openGist(gist: Gist) {
        startActivity(Intent(activity, DetailActivity::class.java)
                .putExtra(GIST_EXTRA, gist))
    }
    //endregion

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
