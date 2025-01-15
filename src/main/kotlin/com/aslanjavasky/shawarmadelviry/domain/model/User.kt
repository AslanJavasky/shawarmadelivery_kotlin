package com.aslanjavasky.shawarmadelviry.domain.model

data class User(
    var id: Long,
    var name: String,
    var email: String,
    var password: String,
    var telegram: String,
    var address: String
)
