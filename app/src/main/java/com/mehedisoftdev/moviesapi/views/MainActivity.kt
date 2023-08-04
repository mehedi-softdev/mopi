package com.mehedisoftdev.moviesapi.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehedisoftdev.moviesapi.MovieApplication
import com.mehedisoftdev.moviesapi.R
import com.mehedisoftdev.moviesapi.adapters.MovieListItemAdapter
import com.mehedisoftdev.moviesapi.databinding.ActivityMainBinding
import com.mehedisoftdev.moviesapi.repository.Resource
import com.mehedisoftdev.moviesapi.viewmodels.MainViewModel
import com.mehedisoftdev.moviesapi.viewmodels.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieListAdapter: MovieListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val repo = (application as MovieApplication).movieRepository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(this, repo))[MainViewModel::class.java]

        // setup adapter
        movieListAdapter = MovieListItemAdapter(this)
        binding.searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchResultsRecyclerView.setHasFixedSize(true)
        binding.searchResultsRecyclerView.adapter = movieListAdapter

        mainViewModel.searchMovieLiveData.observe(this, Observer {
            when(it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.searchResultsRecyclerView.visibility = View.GONE
                    binding.tvMessage.visibility = View.GONE
                    binding.tvTotalResult.text = ""
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if(it.data?.totalResults == null) {
                        binding.tvMessage.visibility = View.VISIBLE
                        binding.searchResultsRecyclerView.visibility = View.GONE
                        binding.tvMessage.text = "No record found for that key"
                        binding.tvTotalResult.text = ""
                    }else {
                        binding.tvMessage.visibility = View.GONE
                        binding.searchResultsRecyclerView.visibility = View.VISIBLE
                        // setup total results
                        binding.tvTotalResult.text = String.format(getString(R.string.total_result), it.data?.totalResults)
                        movieListAdapter.submitList(it.data?.Search)
                    }

                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.searchResultsRecyclerView.visibility = View.GONE
                    binding.tvTotalResult.text = ""
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = it.message.toString()
                    Toast.makeText(this, "Error: ${it.message.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.btnSearch.setOnClickListener {
            btnSearchEvent()
        }

        // enter button event on search edittext
        binding.etSearchKeyword.setOnKeyListener(object: OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnSearchEvent()
                    return true
                }
                return false
            }
        })


    }

    private fun btnSearchEvent() {
        hideKeyboard()
        val searchKeyword: String = binding.etSearchKeyword.text.toString().trim()
        if(searchKeyword == null) {
            Toast.makeText(this, "Enter some keyword", Toast.LENGTH_SHORT).show()
        }else {
            mainViewModel.searchMovies(searchKeyword)
        }
    }

    private fun hideKeyboard() {
        // Only runs if there is a view that is currently focused
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}