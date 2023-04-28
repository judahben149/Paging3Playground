package com.judahben149.paging3trial

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.judahben149.paging3trial.databinding.ItemMovieListBinding

class DiscoverMoviesAdapter(private val context: Context): PagingDataAdapter<DiscoverMoviesResponse.DiscoverMoviesResults, DiscoverMoviesAdapter.DiscoverMoviesViewHolder>(DiscoverMoviesDiffer()) {

    inner class DiscoverMoviesViewHolder(private val binding: ItemMovieListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DiscoverMoviesResponse.DiscoverMoviesResults) {
            binding.tvMovieName.text = item.original_title
            binding.tvDateReleased.text = item.release_date
            Glide.with(context).load(Constants.BACKDROP_BASE_URL + item.poster_path).into(binding.ivMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMoviesViewHolder {
        val binding = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscoverMoviesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DiscoverMoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }



    class DiscoverMoviesDiffer(): DiffUtil.ItemCallback<DiscoverMoviesResponse.DiscoverMoviesResults>() {
        override fun areItemsTheSame(
            oldItem: DiscoverMoviesResponse.DiscoverMoviesResults,
            newItem: DiscoverMoviesResponse.DiscoverMoviesResults
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DiscoverMoviesResponse.DiscoverMoviesResults,
            newItem: DiscoverMoviesResponse.DiscoverMoviesResults
        ): Boolean {
            return oldItem == newItem
        }
    }

}