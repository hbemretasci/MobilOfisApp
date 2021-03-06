package com.codmine.mukellef.domain.model.notifications

data class Notification(
    val id: String,
    val documentName: String,
    val postTime: String,
    val senderUser: String,
    val message: String,
    var readingTime: String
)
