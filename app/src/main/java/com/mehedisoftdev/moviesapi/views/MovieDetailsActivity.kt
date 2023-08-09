package com.mehedisoftdev.moviesapi.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mehedisoftdev.moviesapi.MovieApplication
import com.mehedisoftdev.moviesapi.R
import com.mehedisoftdev.moviesapi.databinding.ActivityMovieDetailsBinding
import com.mehedisoftdev.moviesapi.models.MovieInfo
import com.mehedisoftdev.moviesapi.repository.Resource
import com.mehedisoftdev.moviesapi.viewmodels.MovieDetailsViewModel
import com.mehedisoftdev.moviesapi.viewmodels.MovieDetailsViewModelFactory
import javax.inject.Inject
import kotlin.system.exitProcess

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movieId: String
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    @Inject
    lateinit var movieDetailsViewModelFactory: MovieDetailsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        try {
            movieId = intent.getStringExtra("MOVIEID")!!
        }catch (e: Exception) {
            finishAffinity()
            exitProcess(-1)
        }

        (application as MovieApplication).applicationComponent.injectMovieDetailsActivity(this)

        movieDetailsViewModel = ViewModelProvider(this, movieDetailsViewModelFactory)[MovieDetailsViewModel::class.java]
        // observe movie data
        movieDetailsViewModel.moviesLiveData.observe(this, Observer { it ->
            when(it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.displayLayout.visibility = View.GONE
                    binding.tvMessage.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.displayLayout.visibility = View.VISIBLE
                    binding.tvMessage.visibility = View.GONE
                    it.data?.let { movieInfo ->
                        setUiWithLiveInformation(movieInfo)
                        println(movieInfo.imdbID)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.displayLayout.visibility = View.GONE
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = it.message.toString()
                }
            }
        })

    }

    private fun setUiWithLiveInformation(movieInfo: MovieInfo) {
        // load image
        Glide.with(this).load(movieInfo.Poster).into(binding.ivPoster)
        // title
        binding.tvTitle.text = movieInfo.Title
        // country
        binding.tvCountry.text = String.format(getString(R.string.country), movieInfo.Country)
        // director
        binding.tvDirector.text = String.format(getString(R.string.director), movieInfo.Director)
        // actors
        binding.tvActors.text = String.format(getString(R.string.actors), movieInfo.Actors)
        //released
        binding.tvRelease.text = String.format(getString(R.string.released), movieInfo.Released)
        // dvd
        if(movieInfo.DVD == null)
            binding.tvDvd.text = String.format(getString(R.string.dvd), "N/A")
        else
            binding.tvDvd.text = String.format(getString(R.string.dvd), movieInfo.DVD)
        // duration
        binding.tvDuration.text = String.format(getString(R.string.duration), movieInfo.Runtime)
        // writer
        binding.tvWriter.text = String.format(getString(R.string.writer), movieInfo.Writer)
        // language
        binding.tvLanguage.text = String.format(getString(R.string.language), movieInfo.Language)
        // plot
        binding.tvPlot.text = String.format(getString(R.string.plot), movieInfo.Plot)
        // ratings
        binding.tvRatings.text = String.format(getString(R.string.ratings), movieInfo.imdbRating)
    }

    override fun onStart() {
        super.onStart()
        // load information
        movieDetailsViewModel.getDetailsMovieInfoById(movieId)
    }
}