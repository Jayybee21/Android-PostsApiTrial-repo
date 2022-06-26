package com.example.postsretrieverapi.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.postsretrieverapi.databinding.SinglePostStructureBinding
import com.example.postsretrieverapi.models.PostsSkeleton

class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: SinglePostStructureBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(SinglePostStructureBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    //new function used to reduce api callback time if already exits
    private val diffCallBack = object : DiffUtil.ItemCallback<PostsSkeleton>(){
        override fun areItemsTheSame(oldItem: PostsSkeleton, newItem: PostsSkeleton): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: PostsSkeleton, newItem: PostsSkeleton): Boolean {
            return oldItem == newItem
        }
    }
    //next we call this function to check fr us the values
    private val differ = AsyncListDiffer(this,diffCallBack)

    //lastly we either get or set our values
    var posts : List<PostsSkeleton>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.apply {
            txtSinglePostTitle.text = posts[position].title
            txtSinglePostDescription.text = posts[position].body
        }
    }

    override fun getItemCount() = posts.size

}