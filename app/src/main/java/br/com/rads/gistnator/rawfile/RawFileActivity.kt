package br.com.rads.gistnator.rawfile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.rads.gistnator.R
import br.com.rads.gistnator.RAW_FILE_URL_EXTRA
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.rx.SchedulerProviderImpl
import kotlinx.android.synthetic.main.activity_raw_file.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RawFileActivity : AppCompatActivity(), RawFileContract.View {

    private var presenter: RawFileContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raw_file)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val gistApi = retrofit.create(GistServiceApi::class.java)

        presenter = RawFilePresenter(gistApi, SchedulerProviderImpl())
        presenter?.attachView(this)
        presenter?.loadRawFile(intent.getStringExtra(RAW_FILE_URL_EXTRA))
    }

    override fun onStop() {
        super.onStop()
        presenter?.detachView()
    }

    //region RawFileContract.View
    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showErrorLayout() {
    }

    override fun hideErrorLayout() {
    }

    override fun showRawFile(raw: String) {
        raw_file_textView.text = raw
    }
    //endregion
}
