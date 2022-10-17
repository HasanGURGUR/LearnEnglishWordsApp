package com.learn.vocabulary.model

data class ApiResponseModel (
    val status: String,
    val words: List<WordModel>
)