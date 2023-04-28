package com.judahben149.paging3trial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.paging3trial.databinding.ItemLoadMoreBinding

class LoadMoreAdapter (private val retry: () -> Unit): LoadStateAdapter<LoadMoreAdapter.LoadMoreViewHolder>() {

    inner class LoadMoreViewHolder (private val binding: ItemLoadMoreBinding, retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener { retry() }
        }

        fun setData(state: LoadState) {
            binding.progressBarLoadMore.isVisible = state is LoadState.Loading
            binding.buttonRetry.isVisible = state is LoadState.Error
            binding.retry.isVisible = state is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadMoreViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadMoreViewHolder {
        val binding = ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadMoreViewHolder(binding, retry)
    }
}