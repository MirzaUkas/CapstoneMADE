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
    private var isFavorite: Boolean = false
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

        detailMovie?.let { data ->
            binding.tvOverview.text = data.overview
            binding.tvMatchPercentage.text = getString(R.string.match_percentage, rand(0, 100))
            binding.tvReleaseDate.text = data.releaseDate
            binding.tvMediaType.text = data.voteAverage.toString()
            isFavorite = data.isFavorite
            Glide.with(requireContext())
                .load(IMG_URL + data.imagePoster)
                .into(binding.ivPoster)
            Glide.with(requireContext())
                .load(IMG_URL + data.imageBackdrop)
                .into(binding.ivBackdrop)

            binding.tvFavorite.setOnClickListener {
                isFavorite = !isFavorite
                viewModel.setFavoriteMovie(data, isFavorite)
                setStatusFavorite(isFavorite)
            }
        }
    }

    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
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