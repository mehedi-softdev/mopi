package com.mehedisoftdev.moviesapi.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehedisoftdev.moviesapi.MovieApplication
import com.mehedisoftdev.moviesapi.R
import com.mehedisoftdev.moviesapi.adapters.LoaderAdapter
import com.mehedisoftdev.moviesapi.adapters.MovieListItemAdapter
import com.mehedisoftdev.moviesapi.databinding.ActivityMainBinding
import com.mehedisoftdev.moviesapi.repository.Resource
import com.mehedisoftdev.moviesapi.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieListAdapter: MovieListItemAdapter
    private var key: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // setup adapter
        movieListAdapter = MovieListItemAdapter(this)
        binding.searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchResultsRecyclerView.setHasFixedSize(true)
        binding.searchResultsRecyclerView.adapter = movieListAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        binding.searchResultsRecyclerView.visibility = View.VISIBLE


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
        key = binding.etSearchKeyword.text.toString().trim()
        if(key.isEmpty()) {
            Toast.makeText(this, "Enter some keyword", Toast.LENGTH_SHORT).show()
        }else {
            mainViewModel.searchMovies(key).observe(this, Observer { pagingSearch ->

                movieListAdapter.submitData(lifecycle, pagingSearch)
            })
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