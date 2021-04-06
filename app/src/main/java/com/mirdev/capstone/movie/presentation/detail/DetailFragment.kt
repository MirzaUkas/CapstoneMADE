package com.mirdev.capstone.movie.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mirdev.capstone.core.BuildConfig.IMG_URL
import com.mirdev.capstone.core.domain.model.Movie
import com.mirdev.capstone.movie.R
import com.mirdev.capstone.movie.databinding.DetailFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel()

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showDetailMovie(arguments?.let { DetailFragmentArgs.fromBundle(it).movieData })

    }

    private fun showDetailMovie(detailMovie: Movie?) {
        detailMovie?.let {
            binding.tvOverview.text = detailMovie.overview
            binding.tvReleaseDate.text = detailMovie.releaseDate
            binding.tvMediaType.text = detailMovie.voteAverage.toString()
            Glide.with(requireContext())
                .load(IMG_URL + detailMovie.imagePoster)
                .into(binding.ivPoster)
            Glide.with(requireContext())
                .load(IMG_URL + detailMovie.imageBackdrop)
                .into(binding.ivBackdrop)

            var statusFavorite = detailMovie.isFavorite
            setStatusFavorite(statusFavorite)
            binding.tvFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(detailMovie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.tvFavorite.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_white),
                null,
                null
            )
        } else {
            binding.tvFavorite.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite_white),
                null,
                null
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}