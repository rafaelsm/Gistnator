package br.com.rads.gistnator.gist

data class Gist(val ownerName: String,
                val gistName: String,
                val language: String,
                val rawUrl: String,
                val avatarUrl: String)