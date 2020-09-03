package com.alperenbabagil.cleanappbase.detail.data.profiledetail.model


import com.google.gson.annotations.SerializedName

data class ProfileDetailDataTemplate(
    @SerializedName("dateDOB")
    val dateDOB: String?="dateDOB",
    @SerializedName("personSkill")
    val personSkill: String?="personSkill",
    @SerializedName("personTitle")
    val personTitle: String?="personTitle",
    @SerializedName("userName")
    val userName: String?="personNickname",
    @SerializedName("colorText")
    val colorText: String?="colorText",
    @SerializedName("personMaritalStatus")
    val personMaritalStatus: String?="personMaritalStatus",
    @SerializedName("personLanguage")
    val personLanguage: String?="personLanguage",
    @SerializedName("companyName")
    val companyName: String?="companyName"
)
