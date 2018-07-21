package br.com.rads.gistnator.home

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.rads.gistnator.R
import br.com.rads.gistnator.gist.Gist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_home_gist.view.*

class HomeAdapter(private val gists: MutableList<Gist>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_home_gist, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = gists.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.itemView.gist_owner_name_textView.text = gists[position].ownerName
        holder.itemView.gist_name_textView.text = gists[position].gistName
        holder.itemView.gist_language_textView.text = gists[position].language
        Picasso.get().load(gists[position].avatarUrl).into(holder.itemView.gist_avatar_imageView)

        holder.itemView.setOnClickListener {
            Log.d("TESTE", "ok selecionado")
        }
    }

    fun addAll(newGists: List<Gist>) {
        val index = if (this.gists.size > 0) this.gists.size - 1 else 0
        this.gists.addAll(index, newGists)
        notifyItemRangeChanged(index, newGists.size)
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}