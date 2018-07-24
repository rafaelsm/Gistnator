package br.com.rads.gistnator.favorite


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rads.gistnator.GIST_EXTRA
import br.com.rads.gistnator.R
import br.com.rads.gistnator.detail.DetailActivity
import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.datasource.realm.RealmGistDataSource
import br.com.rads.gistnator.visible
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(), FavoriteContract.View {

    private var presenter: FavoriteContract.Presenter? = null
    private var adater: FavoriteAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = FavoritePresenter(RealmGistDataSource())
        presenter?.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter?.loadFavoriteGists()
    }

    override fun onDestroyView() {
        presenter?.detachView()
        super.onDestroyView()
    }

    //region FavoriteContract.View
    override fun showGists(gists: List<Gist>) {
        adater = FavoriteAdapter(gists.toMutableList(), { presenter?.gistSelected(it) }, { presenter?.removeFromFavorite(it) })
        favorite_recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favorite_recyclerView.adapter = adater
        favorite_recyclerView.visible()
    }

    override fun gistRemoved(gist: Gist) {
        adater?.remove(gist)
    }

    override fun openGist(gist: Gist) {
        startActivity(Intent(activity, DetailActivity::class.java)
                .putExtra(GIST_EXTRA, gist))
    }
    //endregion

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}
