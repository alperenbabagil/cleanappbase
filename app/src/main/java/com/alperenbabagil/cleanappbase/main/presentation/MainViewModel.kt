package com.alperenbabagil.cleanappbase.main.presentation

import androidx.lifecycle.MutableLiveData
import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.cabpresentation.asLiveData
import com.alperenbabagil.cabpresentation.execInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseViewModel
import com.alperenbabagil.cleanappbase.main.domain.GetUsersInteractor
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import com.alperenbabagil.dataholder.DataHolder

class MainViewModel(private val getUsersInteractor:
                    Interactor.SingleInteractor<GetUsersInteractor.Params,List<UserListItem>>)
    : CABDemoBaseViewModel() {

    private val _usersLiveData= MutableLiveData<DataHolder<List<UserListItem>>>()
    val usersLiveData=_usersLiveData.asLiveData()

    fun getUsers(requestResultType: RequestResultType){
        execInteractor(_usersLiveData,
            getUsersInteractor,
            GetUsersInteractor.Params(requestResultType))
    }
}