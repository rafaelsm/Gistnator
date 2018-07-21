package br.com.rads.gistnator.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import br.com.rads.gistnator.R
import br.com.rads.gistnator.detail.DetailActivity
import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.rx.SchedulerProviderImpl
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class HomeFragment : Fragment(), HomeContract.View {

    private var adater: HomeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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
        val homePresenter = HomePresenter(gistApi, SchedulerProviderImpl())

        adater = HomeAdapter(mutableListOf()) { homePresenter.gistSelected(it) }
        context?.let {
            home_recyclerView.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            home_recyclerView.adapter = adater
        }

        homePresenter.attachView(this)
        homePresenter.loadGists()
    }


    //region HomeContract.View
    override fun hideMainProgress() {
    }

    override fun showErrorLoadingGists() {
    }

    override fun hideErrorLoadingGists() {
    }

    override fun showToastErrorLoadingGists() {
        Toast.makeText(context, "fuck", Toast.LENGTH_SHORT).show()
    }

    override fun addGistsToList(gists: List<Gist>) {
        home_recyclerView.visibility = View.VISIBLE
        adater?.addAll(gists)
    }

    override fun openGist(gist: Gist) {
        startActivity(Intent(activity, DetailActivity::class.java).putExtra("GIST_EXTRA",gist))
    }
    //endregion

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
