package com.examz.testapp.ui.detail.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.examz.testapp.R
import com.examz.testapp.data.api.ApiHelper
import com.examz.testapp.data.api.RetrofitBuilder
import com.examz.testapp.data.model.Breed
import com.examz.testapp.data.model.BreedWithImage
import com.examz.testapp.ui.base.ViewModelFactory
import com.examz.testapp.ui.detail.viewmodel.DetailViewModel
import com.examz.testapp.ui.main.adapter.BREED_ID
import com.examz.testapp.utils.Resource
import com.examz.testapp.utils.Status
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_main.*


public class DetailActivity : AppCompatActivity(R.layout.activity_detail) {

    private lateinit var viewModel: DetailViewModel
    private var breedID: String = ""

    companion object {
        fun start(context: Context, bundle: Bundle) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        this.intent?.extras?.let {
            breedID = it.getString(BREED_ID).toString()
        }

        setupViewModel()
        setupObservers(breedID)
    }

    private fun setupObservers(breedID: String?) {
        viewModel.getBreedByID(breedID ?: "").observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let(this@DetailActivity::onDataReceived)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun onDataReceived(breed: Breed) {
        cat_info.text = breed.description

        viewModel.getBreedImage(breed.referenceImageId).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loadCatImage(resource)
                    }
                    Status.ERROR -> {
                        image_progress_bar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        image_progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun loadCatImage(resource: Resource<BreedWithImage>) {
        Glide.with(this).load(resource.data?.url ?: "")
            .listener(object :
                RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("TAG", "onLoadFailed: Loaing failed")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    image_progress_bar.visibility = View.GONE
                    return false
                }
            }).into(cat_image_view)
    }


    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)
    }
}