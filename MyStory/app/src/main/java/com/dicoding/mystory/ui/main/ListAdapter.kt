package com.dicoding.mystory.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mystory.data.response.ListStoryItem
import com.dicoding.mystory.databinding.ItemStoryBinding
import com.dicoding.mystory.ui.detail.DetailActivity
import com.dicoding.mystory.utils.loadImage

class ListAdapter(private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            binding.apply {
                item.photoUrl?.let { imgItem.loadImage(it) }
                tvTitle.text = item.name
                tvDescription.text = item.description
                itemView.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgItem, "image"),
                            Pair(tvTitle, "name"),
                            Pair(tvDescription, "description")
                        )
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.ID, item.id)
                    itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listStory[position])
    }
}