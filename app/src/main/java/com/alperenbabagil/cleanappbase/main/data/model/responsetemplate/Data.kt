package com.alperenbabagil.cleanappbase.main.data.model.responsetemplate


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("dateDOB")
    val dateDOB: String?="dateDOB",
    @SerializedName("personSkill")
    val personSkill: String?="personSkill",
    @SerializedName("personTitle")
    val personTitle: String?="personTitle",
    @SerializedName("_repeat")
    val repeat: Int?=10,
    @SerializedName("userName")
    val userName: String?="personNickname"
)