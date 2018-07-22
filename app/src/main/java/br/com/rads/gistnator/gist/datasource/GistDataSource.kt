package br.com.rads.gistnator.gist.datasource

import br.com.rads.gistnator.gist.Gist

interface GistDataSource {
    fun save(gist: Gist)
    fun delete(gist: Gist)
    fun listGists(): List<Gist>
}