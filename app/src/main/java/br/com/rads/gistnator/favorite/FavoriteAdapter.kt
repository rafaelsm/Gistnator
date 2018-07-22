package br.com.rads.gistnator.favorite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rads.gistnator.R
import br.com.rads.gistnator.gist.Gist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_favorite_gist.view.*

class FavoriteAdapter(private val gists: List<Gist>,
                      private val selectGist: (Gist) -> Unit,
                      private val deleteGist: (Gist) -> Unit)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_favorite_gist, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = gists.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(gists[position])
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(gist: Gist) {
            itemView.gist_owner_name_textView.text = gist.ownerName
            itemView.gist_name_textView.text = gist.gistName
            itemView.gist_language_textView.text = gist.language
            Picasso.get().load(gist.avatarUrl).into(itemView.gist_avatar_imageView)

            itemView.setOnClickListener {
                selectGist(gist)
            }

            itemView.delete_gist_imageButton.setOnClickListener {
                deleteGist(gist)
            }
        }

    }
}