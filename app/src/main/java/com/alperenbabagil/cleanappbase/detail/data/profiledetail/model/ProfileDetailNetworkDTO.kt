package com.alperenbabagil.cleanappbase.detail.data.profiledetail.model

import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.cleanappbase.main.data.MainDataConstants
import com.alperenbabagil.cleanappbase.main.data.model.mapToUserListItem
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class ProfileDetailNetworkDTO(@SerializedName("userName") val username:String?=null,
                                   @SerializedName("personTitle") val title: String?=null,
                                   @SerializedName("personSkill") val skill:String?=null,
                                   @SerializedName("dateDOB") val dateDOB: String?,
                                   @SerializedName("colorText") val favouriteColor:String?=null,
                                   @SerializedName("personMaritalStatus") val personMaritalStatus:String?=null,
                                   @SerializedName("personLanguage") val personLanguage:String?=null,
                                   @SerializedName("companyName") val companyName:String?=null)

fun ProfileDetailNetworkDTO.mapToProfileDetail() : ProfileDetail{
    return ProfileDetail(username = this.username!!,
        title = this.title!!,
        skill = this.skill!!,
        ppUrl = "${MainDataConstants.PP_BASE_URL}${this.username}",
        birthDate = this.dateDOB.let {
            val df= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            df.parse(this.dateDOB!!)!!
        },
        favouriteColor = favouriteColor,
        personMaritalStatus = personMaritalStatus,
        personLanguage = personLanguage,
        companyName = companyName
        )
}

