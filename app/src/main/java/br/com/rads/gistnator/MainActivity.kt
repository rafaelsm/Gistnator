package br.com.rads.gistnator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.rads.gistnator.about.AboutFragment
import br.com.rads.gistnator.favorite.FavoriteFragment
import br.com.rads.gistnator.gist.GistServiceApi
import br.com.rads.gistnator.home.HomeFragment
import br.com.rads.gistnator.home.HomePresenter
import br.com.rads.gistnator.rx.SchedulerProviderImpl
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment.newInstance()
    private val favoriteFragment = FavoriteFragment.newInstance()
    private val aboutFragment = AboutFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> if (item.isChecked.not()) showHomeFragment()
                R.id.navigation_favorite -> if (item.isChecked.not()) showFavoriteFragment()
                R.id.navigation_about -> if (item.isChecked.not()) showAboutFragment()
            }
            true
        }

        showHomeFragment()
    }

    private fun showFavoriteFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, favoriteFragment)
                .addToBackStack(null)
                .commit()
    }

    private fun showAboutFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, aboutFragment)
                .addToBackStack(null)
                .commit()
    }

    private fun showHomeFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, homeFragment)
                .addToBackStack(null)
                .commit()
    }
}
