package com.mirdev.capstone.favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirdev.capstone.core.ui.MovieAdapter
import com.mirdev.capstone.favorite.databinding.FragmentFavoriteBinding
import com.mirdev.capstone.favorite.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)
        val movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            view.findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment3(selectedData)
            )
        }

        favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner, { movie ->
            if (movie != null && movie.isNotEmpty()) {
                movieAdapter.setData(true, movie)
            } else {
                binding.progressBar.visibility = View.GONE
                binding.viewErrorFavorite.root.visibility = View.VISIBLE
                binding.viewErrorFavorite.tvError.text =
                    getString(com.mirdev.capstone.core.R.string.no_data)
            }
        })

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}