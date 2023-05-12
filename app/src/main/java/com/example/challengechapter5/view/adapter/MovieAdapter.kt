package com.example.challengechapter5.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter5.databinding.ItemMovieBinding
import com.example.challengechapter5.model.ResultPopular

class MovieAdapter(var listMovie:List<ResultPopular>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    var onClick: ((ResultPopular)->Unit)? = null
    class ViewHolder(var binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list =listMovie[position]
        holder.binding.titleMovie.text = list.title
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w400${list.posterPath}")
            .into(holder.binding.ivMovie)
        holder.binding.areaDetail.setOnClickListener{
            onClick?.invoke(list)
        }
        holder.binding.rateMovie.text = list.voteAverage.toString()
    }
}