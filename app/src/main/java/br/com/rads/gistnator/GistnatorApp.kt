package br.com.rads.gistnator

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


class GistnatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("gistnator.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }
}