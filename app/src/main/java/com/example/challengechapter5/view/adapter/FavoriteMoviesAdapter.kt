package com.example.challengechapter5.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter5.database_room.FavoriteMoviesData
import com.example.challengechapter5.databinding.ItemFavoriteMovieBinding


class FavoriteMoviesAdapter(private val listFavoriteMovies: List<FavoriteMoviesData>) : RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {
    var onClick: ((FavoriteMoviesData)->Unit)? = null
    class ViewHolder(var binding:ItemFavoriteMovieBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFavoriteMovies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listFavoriteMovies[position]
        holder.binding.titleMovie.text = list.title
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w400${list.posterPath}")
            .into(holder.binding.ivMovie)
        holder.binding.onClickDetail.setOnClickListener{
            onClick?.invoke(list)
        }
        holder.binding.rateMovie.text = list.voteAverage.toString()
    }
}