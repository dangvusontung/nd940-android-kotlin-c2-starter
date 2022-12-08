package com.udacity.asteroidradar.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidAdapter(private val listener: AsteroidListItemSelectedListener) :
    ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding =
            ItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteroidViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
    }
}

class AsteroidViewHolder(
    private val binding: ItemAsteroidBinding,
    private val listener: AsteroidListItemSelectedListener
) : ViewHolder(binding.root) {

    fun bind(asteroid: Asteroid) {
        binding.asteroid = asteroid
        binding.listener = listener
        binding.executePendingBindings()
    }
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidListItemSelectedListener(private val action: (Asteroid) -> Unit) {
    fun onItemSelected(item: Asteroid) {
        action(item)
    }
}