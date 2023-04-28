package com.judahben149.paging3trial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.paging3trial.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var adapter: DiscoverMoviesAdapter? = null
    private lateinit var recyclerView: RecyclerView


    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        lifecycleScope.launchWhenCreated {
            adapter?.loadStateFlow?.collect {
                val state = it.refresh
                binding.prgBar.isVisible = state is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.moviesList.collect { moviesList ->
                adapter?.submitData(moviesList)
            }
        }

    }

    private fun initViews() {
        adapter = DiscoverMoviesAdapter(requireContext())
        recyclerView = binding.rvMovieList

        recyclerView.adapter = adapter!!.withLoadStateFooter(LoadMoreAdapter{ adapter!!.retry()})
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}