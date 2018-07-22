package br.com.rads.gistnator.gist.datasource.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmGist(
        var gistId: String? = null,
        var ownerName: String? = null,
        var gistName: String? = null,
        var language: String? = null,
        var rawUrl: String? = null,
        var avatarUrl: String? = null) : RealmObject()