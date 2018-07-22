package br.com.rads.gistnator.rawfile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.rads.gistnator.R

class RawFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raw_file)
        intent.getStringExtra("RAW_FILE_URL_EXTRA")
    }
}
