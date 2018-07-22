package br.com.rads.gistnator.gist.response

data class FileContent(
        val size: Int,
        val raw_url: String,
        val type: String,
        val truncated: Boolean,
        val language: String?
)