package com.learn.vocabulary.data.remote

import com.learn.vocabulary.model.ApiResponseModel
import com.learn.vocabulary.model.CategoriesModel
import com.learn.vocabulary.model.WordsByCategoriesModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class VocabularyInstance {

    private val BASE_URL ="http://10.0.2.2/learnVocabulary/v1/"
    private val api  = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun getVocabulary() : Single<ApiResponseModel> {
        return api.getVocabulary()
    }
    fun getCategories() : Single<CategoriesModel>{
        return api.getAllCategories()
    }
    fun getWordsByCategories(cat_id : Int) : Single<WordsByCategoriesModel>{
        return api.getWordsByCategories(cat_id)
    }
}