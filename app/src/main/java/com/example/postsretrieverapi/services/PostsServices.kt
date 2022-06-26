package com.example.postsretrieverapi.services

import com.example.postsretrieverapi.models.PostsSkeleton
import retrofit2.Response
import retrofit2.http.GET

interface PostsServices {

    @GET("/posts")
    suspend fun getPosts() : Response<List<PostsSkeleton>>

}