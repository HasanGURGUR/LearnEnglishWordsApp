package com.learn.vocabulary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.vocabulary.data.remote.VocabularyInstance
import com.learn.vocabulary.model.ApiResponseModel
import com.learn.vocabulary.model.CategoriesModel
import com.learn.vocabulary.model.WordsByCategoriesModel
import com.learn.vocabulary.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class VocabularyViewModel : ViewModel() {

    val words = MutableLiveData<ApiResponseModel>()
    val categories = SingleLiveEvent<CategoriesModel>()
    val wordsByCategories = MutableLiveData<WordsByCategoriesModel>()

    private val wordService: VocabularyInstance = VocabularyInstance()
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun fetchDataFromRemoteApi() {
        disposable.add(
            wordService.getVocabulary()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseModel>() {
                    override fun onSuccess(response: ApiResponseModel) {

                        words.value = response
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun fetchCategoriesFromRemoteApi() {

        disposable.add(
            wordService.getCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CategoriesModel>() {
                    override fun onSuccess(response: CategoriesModel) {

                        categories.value = response
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun fetchWordsByCategoriesFromRemoteApi(cat_id : Int) {

        disposable.add(
            wordService.getWordsByCategories(cat_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WordsByCategoriesModel>() {
                    override fun onSuccess(response: WordsByCategoriesModel) {

                        wordsByCategories.value = response
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }


}