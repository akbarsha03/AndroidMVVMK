package com.examz.testapp.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.examz.testapp.R
import com.examz.testapp.data.api.ApiHelper
import com.examz.testapp.data.api.RetrofitBuilder
import com.examz.testapp.data.model.Breed
import com.examz.testapp.ui.base.ViewModelFactory
import com.examz.testapp.ui.main.adapter.MainAdapter
import com.examz.testapp.ui.main.viewmodel.MainViewModel
import com.examz.testapp.utils.Status
import kotlinx.android.synthetic.main.content_main.*

const val DEFAULT_LIMIT = 50

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getBreeds(DEFAULT_LIMIT).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recycler_view.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                        resource.data?.let { breeds -> retrieveList(breeds) }

                    }
                    Status.ERROR -> {
                        recycler_view.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                        recycler_view.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupUI() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter((arrayListOf()))
//        recycler_view.addItemDecoration(
//            DividerItemDecoration(
//                recycler_view.context,
//                (recycler_view.layoutManager as LinearLayoutManager).orientation
//            )
//        )
        recycler_view.adapter = adapter
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    private fun retrieveList(breeds: List<Breed>) {
        adapter.apply {
            addBreeds(breeds)
            notifyDataSetChanged()
        }
    }
}