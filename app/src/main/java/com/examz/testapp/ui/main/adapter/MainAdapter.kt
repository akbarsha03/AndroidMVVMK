package com.examz.testapp.ui.main.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examz.testapp.R
import com.examz.testapp.data.model.Breed
import com.examz.testapp.ui.detail.view.DetailActivity
import kotlinx.android.synthetic.main.row_item.view.*

const val BREED_ID = "breed_id"

class MainAdapter(private val breeds: ArrayList<Breed>) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(breed: Breed) {
            itemView.apply {
                breed_name.text = breed.name
                breed_info.text = "Weight: ${breed.weight.imperial}"
                row_item.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString(BREED_ID, breed.id)
                    DetailActivity.start(context, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(breeds[position])
    }

    override fun getItemCount(): Int = breeds.size

    fun addBreeds(breeds: List<Breed>) {
        this.breeds.apply {
            clear()
            addAll(breeds)
        }
    }
}