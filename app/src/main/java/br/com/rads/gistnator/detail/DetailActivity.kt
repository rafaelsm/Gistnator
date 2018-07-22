package br.com.rads.gistnator.detail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.rads.gistnator.GIST_EXTRA
import br.com.rads.gistnator.R
import br.com.rads.gistnator.RAW_FILE_URL_EXTRA
import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.rawfile.RawFileActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var detailPresenter: DetailContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val gist = intent.getParcelableExtra<Gist>(GIST_EXTRA)
        gist_owner_textView.text = gist.ownerName
        gist_name_textView.text = gist.gistName
        gist_language_textView.text = gist.language
        Picasso.get().load(gist.avatarUrl).into(gist_avatar_imageView)

        detailPresenter = DetailPresenter(gist)

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

    //region DetailContract.View
    override fun openRawFile(rawFileUrl: String) {
        startActivity(Intent(this, RawFileActivity::class.java)
                .putExtra(RAW_FILE_URL_EXTRA, rawFileUrl))
    }
    //endregion
}
