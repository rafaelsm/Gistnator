package br.com.rads.gistnator.gist.datasource.realm

import br.com.rads.gistnator.gist.Gist
import br.com.rads.gistnator.gist.datasource.GistDataSource
import io.realm.Realm

class RealmGistDataSource : GistDataSource {

    override fun save(gist: Gist) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val realmGist1 = realm.createObject(RealmGist::class.java)
        realmGist1.gistId = gist.gistId
        realmGist1.ownerName = gist.ownerName
        realmGist1.gistName = gist.gistName
        realmGist1.language = gist.language
        realmGist1.rawUrl = gist.rawUrl
        realmGist1.avatarUrl = gist.avatarUrl
        realm.commitTransaction()
    }

    override fun delete(gist: Gist) {
        val realm = Realm.getDefaultInstance()
        val realmGist = realm.where(RealmGist::class.java).equalTo("gistId", gist.gistId).findFirst()
        realm.executeTransaction {
            realmGist?.deleteFromRealm()
        }
    }

    override fun listGists(): List<Gist> {
        val realm = Realm.getDefaultInstance()
        return realm.where(RealmGist::class.java).findAll()
                .map {
                    Gist(it.gistId ?: "",
                            it.ownerName ?: "",
                            it.gistName ?: "",
                            it.language ?: "",
                            it.rawUrl ?: "",
                            it.avatarUrl ?: "")
                }
    }

}