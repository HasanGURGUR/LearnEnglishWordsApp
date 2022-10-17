package com.learn.vocabulary.data.remote

import com.learn.vocabulary.model.ApiResponseModel
import com.learn.vocabulary.model.CategoriesModel
import com.learn.vocabulary.model.WordModel
import com.learn.vocabulary.model.WordsByCategoriesModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/getAllWords.php")
    fun getVocabulary() : Single<ApiResponseModel>

    @GET("categories/getAllCategories.php")
    fun getAllCategories() : Single<CategoriesModel>

    @GET("words/getWordsByCategories.php")
    fun getWordsByCategories(@Query("cat_id") cat_id : Int) : Single<WordsByCategoriesModel>
}