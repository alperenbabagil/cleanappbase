package com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model

import java.util.*

data class ProfileDetail(val username:String?=null,
                    val title: String?=null,
                    val ppUrl:String?=null,
                    val skill:String?=null,
                    val birthDate: Date?=null,
                    val favouriteColor:String?=null,
                    val personMaritalStatus:String?=null,
                    val personLanguage:String?=null,
                    val companyName:String?=null
)

