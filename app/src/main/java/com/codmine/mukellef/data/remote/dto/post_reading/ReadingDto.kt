package com.codmine.mukellef.data.remote.dto.post_reading

import com.google.gson.annotations.SerializedName

data class ReadingDto(
    @SerializedName("Msg")
    val readingMessage: String,
    @SerializedName("OkumaSonucu")
    val readingResult: String
)