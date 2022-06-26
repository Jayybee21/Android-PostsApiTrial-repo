package com.example.postsretrieverapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postsretrieverapi.databinding.ActivityPostsBinding
import com.example.postsretrieverapi.services.RetrofitPostsClass
import com.example.postsretrieverapi.ui.utils.PostsAdapter
import retrofit2.HttpException
import java.io.IOException

class PostsActivity : AppCompatActivity() {

    private lateinit var postsadapter: PostsAdapter
    private lateinit var binding: ActivityPostsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecView() //calling our private recyclerview

        //coroutine scope used for loading the data online
        lifecycleScope.launchWhenCreated {
            //calling progress call to make it visible
            binding.progressbarPostApi.isVisible= true

            //surrounding with try catch in case user has issues with connection or else

            val response = try {
                //todos list called
                RetrofitPostsClass.postapi.getPosts()
            }catch(e: IOException){
                Log.d("ApiResultError", "Check internet connection ")
                binding.progressbarPostApi.isVisible= false
                Toast.makeText(this@PostsActivity,"Connection Error !", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            }catch(e: HttpException){
                Log.d("ApiResultError", "Unexpected response")
                binding.progressbarPostApi.isVisible= false
                Toast.makeText(this@PostsActivity,"Server Error !", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null){
                //filling data following the json received from our api !
                postsadapter.posts = response.body()!!
            }else{
                Toast.makeText(this@PostsActivity,"No data returned !", Toast.LENGTH_SHORT).show()
                Log.d("ApiResultError", "No results were returned !")
            }
            binding.progressbarPostApi.isVisible= false
        }
    }


    //creating own function for recyclerview to not always call recyclerview. ....
    private fun setupRecView() = binding.rvPosts.apply{
        postsadapter = PostsAdapter()
        adapter = postsadapter
        layoutManager = LinearLayoutManager(this@PostsActivity)
    }
}