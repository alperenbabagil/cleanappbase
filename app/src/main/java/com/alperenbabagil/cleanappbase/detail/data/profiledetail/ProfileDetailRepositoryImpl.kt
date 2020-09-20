package com.alperenbabagil.cleanappbase.detail.data.profiledetail

import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cabdomain.model.handleSuccess
import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.core.data.model.Error
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailDataTemplate
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailNetworkDTO
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.mapToProfileDetail
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.ProfileDetailRepository
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail

class ProfileDetailRepositoryImpl(
    private val profileDetailNetworkDataSource:
    BaseDataSource<ResponseTemplate<ProfileDetailDataTemplate>, ProfileDetailNetworkDTO>
) : ProfileDetailRepository {
    override suspend fun getProfileDetail(
        userId: String,
        requestResultType: RequestResultType
    ): DataHolder<ProfileDetail> {
        val requestTemplate=when(requestResultType){
            RequestResultType.SUCCESS->{
                ResponseTemplate(data= ProfileDetailDataTemplate(userName = userId))
            }
            RequestResultType.FAIL->{
                ResponseTemplate<ProfileDetailDataTemplate>().apply {
                    status= CoreDataConstants.SERVER_STATUS_FAIL
                    data=null
                    error= Error(
                        7,
                        "Server business error"
                    )
                }
            }
        }
        return profileDetailNetworkDataSource.getResult(requestTemplate).handleSuccess {
            it.data.mapToProfileDetail()
        }
    }
}