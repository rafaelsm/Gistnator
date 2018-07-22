package br.com.rads.gistnator.gist.response

import org.json.JSONObject

data class GistsResponse(
        val url: String,
        val forks_url: String,
        val commits_url: String,
        val id: String,
        val node_id: String,
        val description: String,
        val public: Boolean,
        val owner: Owner,
        val user: Any,
        val files: Map<String, FileContent>,
        val truncated: Boolean,
        val comments: Int,
        val comments_url: String,
        val html_url: String,
        val git_pull_url: String,
        val git_push_url: String,
        val created_at: String,
        val updated_at: String
)