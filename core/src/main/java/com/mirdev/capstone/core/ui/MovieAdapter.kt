package com.mirdev.capstone.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirdev.capstone.core.BuildConfig.IMG_URL
import com.mirdev.capstone.core.R
import com.mirdev.capstone.core.databinding.ItemMovieBinding
import com.mirdev.capstone.core.databinding.ItemMovieLinearBinding
import com.mirdev.capstone.core.domain.model.Movie
import java.util.*

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null
    private var isLinearItem = true

    fun setData(isLinear: Boolean, newListData: List<Movie>?) {
        isLinearItem = isLinear
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (isLinearItem)
            LinearViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_movie_linear,
                    parent,
                    false
                )
            )
        else
            GridViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_movie,
                    parent,
                    false
                )
            )


    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = listData[position]
        if (isLinearItem)
            (holder as LinearViewHolder).bind(data)
        else
            (holder as GridViewHolder).bind(data)
    }

    inner class LinearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieLinearBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                tvTitle.text = data.title
                Glide.with(itemView.context)
                    .load(IMG_URL + data.imageBackdrop)
                    .into(ivBanner)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }


    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(IMG_URL + data.imagePoster)
                    .into(ivMovie)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}