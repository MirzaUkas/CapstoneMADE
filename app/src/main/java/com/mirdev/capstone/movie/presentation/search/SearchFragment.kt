package com.mirdev.capstone.movie.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirdev.capstone.core.data.Resource
import com.mirdev.capstone.core.ui.MovieAdapter
import com.mirdev.capstone.movie.R
import com.mirdev.capstone.movie.databinding.SearchFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {


    private val viewModel: SearchViewModel by viewModel()
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            view.findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(selectedData))

        }

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        fetchSearch(movieAdapter)

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun fetchSearch(movieAdapter: MovieAdapter) {
        val result = binding.svMovie.getQueryTextChangeStateFlow()
            .debounce(300)
            .filter {
                it.trim().isEmpty().not()
            }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                viewModel.getMovie(query)
            }.asLiveData()

        result.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    movieAdapter.setData(true, it.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.viewErrorSearch.root.visibility = View.VISIBLE
                    binding.viewErrorSearch.tvError.text =
                        it.message ?: getString(R.string.something_wrong)
                }
            }
        })


    }


    private fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

        val query = MutableStateFlow("")

        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                query.value = newText
                return true
            }
        })

        return query

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}