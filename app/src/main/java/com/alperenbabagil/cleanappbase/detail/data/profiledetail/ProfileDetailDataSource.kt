package com.alperenbabagil.cleanappbase.detail.data.profiledetail

import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.detail.data.ProfileService
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailDataTemplate
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailNetworkDTO

class ProfileDetailDataSource(
    private val profileService: ProfileService,
    private val apiCallAdapter: ApiCallAdapter
) : BaseDataSource<ResponseTemplate<ProfileDetailDataTemplate>, ProfileDetailNetworkDTO>() {

    override suspend fun getDataSourceResult(request: BaseRequest<ResponseTemplate<ProfileDetailDataTemplate>>):
            DataHolder<ProfileDetailNetworkDTO> = apiCallAdapter.adapt {
        profileService.getProfileDetail(request)
    }

}