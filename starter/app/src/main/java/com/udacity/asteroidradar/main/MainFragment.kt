package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.utils.DateUtil
import com.udacity.asteroidradar.utils.MainViewModelFactory

private const val TAG = "MainFragment"

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            MainViewModelFactory(activity.application)
        ).get(MainViewModel::class.java)
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: AsteroidAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        val listener = AsteroidListItemSelectedListener {
            viewModel.asteroidItemSelected(it)
        }

        adapter = AsteroidAdapter(listener)
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(context)
        binding.asteroidRecycler.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let {
                Log.d(TAG, "observe list: ${it.size}")
                adapter.submitList(it)
            }

        }

        viewModel.pictureOfDay.observe(viewLifecycleOwner) {
            it?.let {
                binding.picOfDay = it
            }
        }

        viewModel.selectedAsteroid.observe(viewLifecycleOwner) {
            it?.let {
                val bundle = bundleOf("selectedAsteroid" to it)
                this.findNavController().navigate(R.id.action_showDetail, bundle)
                viewModel.asteroidItemSelected(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today_asteroid -> {
                viewModel.filterAsteroidByDay()
            }

            R.id.show_week_asteroid -> {
                viewModel.filterAsteroidByWeek()
            }

            R.id.show_saved_asteroid -> {
                viewModel.filterAllAsteroid()
            }
            else -> {}
        }
        return true
    }
}
