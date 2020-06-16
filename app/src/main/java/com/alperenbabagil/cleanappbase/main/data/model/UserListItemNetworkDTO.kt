package com.alperenbabagil.cleanappbase.main.data.model

import com.alperenbabagil.cleanappbase.main.data.MainDataConstants.Companion.PP_BASE_URL
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class UserListItemNetworkDTO(@SerializedName("userName") val personNickname:String?,
                                  @SerializedName("personTitle") val personTitle: String?,
                                  @SerializedName("personSkill") val personSkill:String?,
                                  @SerializedName("dateDOB") val dateDOB: String?
)

fun UserListItemNetworkDTO.mapToUserListItem() : UserListItem{
    return UserListItem(username = this.personNickname!!,
        title = this.personTitle!!,
        skill = this.personSkill!!,
        ppUrl = "$PP_BASE_URL${this.personNickname}",
        birthDate = this.dateDOB.let {
            val df= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            df.parse(this.dateDOB!!)!!
            }
        )
}

