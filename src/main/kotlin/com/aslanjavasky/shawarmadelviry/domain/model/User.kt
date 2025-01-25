package com.aslanjavasky.shawarmadelviry.domain.model

data class User(
    var id: Long?=null,
    var name: String?=null,
    var email: String?=null,
    var password: String?=null,
    var phone: String?=null,
    var telegram: String?=null,
    var address: String?=null
)
