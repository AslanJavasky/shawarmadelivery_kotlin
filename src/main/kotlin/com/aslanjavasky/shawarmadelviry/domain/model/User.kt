package com.aslanjavasky.shawarmadelviry.domain.model

data class User(
    override var id: Long? = null,
    override var name: String? = null,
    override var email: String? = null,
    override var password: String? = null,
    override var phone: String? = null,
    override var telegram: String? = null,
    override var address: String? = null
) : IUser
