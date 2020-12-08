package com.examz.testapp.ui.detail.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.examz.testapp.R
import com.examz.testapp.data.model.Breed
import com.examz.testapp.data.model.BreedWithImage
import com.examz.testapp.ui.detail.viewmodel.DetailViewModel
import com.examz.testapp.ui.main.adapter.BREED_ID
import com.examz.testapp.utils.Resource
import com.examz.testapp.utils.Status
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject


class DetailActivity : DaggerAppCompatActivity(R.layout.activity_detail) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailViewModel> { viewModelFactory }

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

        setupObservers(breedID)
    }

    private fun setupObservers(breedID: String?) {
        viewModel.getBreedByID(breedID ?: "").observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let(this@DetailActivity::onDataReceived)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun onDataReceived(breed: Breed) {
        cat_info.text = breed.description

        viewModel.getBreedImage(breed.referenceImageId).observe(this) {
            it.let { resource ->
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
        }
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
}