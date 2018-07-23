package br.com.rads.gistnator.rawfile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.rads.gistnator.R
import br.com.rads.gistnator.RAW_FILE_URL_EXTRA
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.gone
import br.com.rads.gistnator.rx.SchedulerProviderImpl
import br.com.rads.gistnator.visible
import kotlinx.android.synthetic.main.activity_raw_file.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RawFileActivity : AppCompatActivity(), RawFileContract.View {

    private var presenter: RawFileContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raw_file)

        val rawUrl = intent.getStringExtra(RAW_FILE_URL_EXTRA)
        presenter = RawFilePresenter(GistServiceApi.getService(), SchedulerProviderImpl())
        presenter?.attachView(this)
        presenter?.loadRawFile(rawUrl)
        try_again_button.setOnClickListener {
            presenter?.loadRawFile(rawUrl)
        }
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    //region RawFileContract.View
    override fun showProgress() {
        raw_progressBar.visible()
    }

    override fun hideProgress() {
        raw_progressBar.gone()
    }

    override fun showErrorLayout() {
        error_linearLayout.visible()
    }

    override fun hideErrorLayout() {
        error_linearLayout.gone()
    }

    override fun showRawFile(raw: String) {
        raw_file_textView.text = raw
    }
//endregion
}
