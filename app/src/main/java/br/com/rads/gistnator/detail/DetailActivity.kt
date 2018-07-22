package br.com.rads.gistnator.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.rads.gistnator.GIST_EXTRA
import br.com.rads.gistnator.R
import br.com.rads.gistnator.RAW_FILE_URL_EXTRA
import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.datasource.GistDataSource
import br.com.rads.gistnator.gist.datasource.realm.RealmGistDataSource
import br.com.rads.gistnator.rawfile.RawFileActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var detailPresenter: DetailContract.Presenter? = null
    private var favoriteMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val gist = intent.getParcelableExtra<Gist>(GIST_EXTRA)
        gist_owner_textView.text = gist.ownerName
        gist_name_textView.text = gist.gistName
        gist_language_textView.text = gist.language
        Picasso.get().load(gist.avatarUrl).into(gist_avatar_imageView)

        detailPresenter = DetailPresenter(gist, RealmGistDataSource())

        show_raw_file_button.setOnClickListener {
            detailPresenter?.showRawFileSelected()
        }
    }

    override fun onStart() {
        super.onStart()
        detailPresenter?.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        detailPresenter?.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        menu?.findItem(R.id.menu_favorite)?.apply {
            if (detailPresenter?.gistIsFavorite() == true) {
                isChecked = true
                setIcon(R.drawable.ic_favorite_black_24dp)
            } else {
                isChecked = false
                setIcon(R.drawable.ic_favorite_border_black_24dp)
            }
        }
        this.favoriteMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_favorite) {
            val checked = item.isChecked
            if (checked) {
                detailPresenter?.removeFromFavorites()
            } else {
                detailPresenter?.addToFavorites()
            }
            item.isChecked = !checked
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //region DetailContract.View
    override fun openRawFile(rawFileUrl: String) {
        startActivity(Intent(this, RawFileActivity::class.java)
                .putExtra(RAW_FILE_URL_EXTRA, rawFileUrl))
    }

    override fun gistAddedAsFavorites() {
        favoriteMenu?.getItem(0)?.setIcon(R.drawable.ic_favorite_black_24dp)
    }

    override fun gistRemovedFromFavorites() {
        favoriteMenu?.getItem(0)?.setIcon(R.drawable.ic_favorite_border_black_24dp)
    }
    //endregion
}
