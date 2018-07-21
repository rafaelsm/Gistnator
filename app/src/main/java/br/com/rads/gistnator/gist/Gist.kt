package br.com.rads.gistnator.gist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gist(val ownerName: String,
                val gistName: String,
                val language: String,
                val rawUrl: String,
                val avatarUrl: String) : Parcelable