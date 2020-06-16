package com.alperenbabagil.cleanappbase.main.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperenbabagil.cabpresentation.observeDataHolder
import com.alperenbabagil.cabpresentation.showYesNoDialog
import com.alperenbabagil.cleanappbase.R
import com.alperenbabagil.cleanappbase.core.presentation.AppNavigator
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseActivity
import com.alperenbabagil.cleanappbase.databinding.UserListItemRowBinding
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import com.alperenbabagil.cleanappbase.main.domain.model.UserListRequestType
import com.alperenbabagil.simpleanimationpopuplibrary.SapDialog
import com.otaliastudios.elements.Adapter
import com.otaliastudios.elements.Element
import com.otaliastudios.elements.Page
import com.otaliastudios.elements.extensions.DataBindingPresenter
import com.otaliastudios.elements.extensions.ListSource
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


class MainActivity : CABDemoBaseActivity() {

    override val cabViewModel: MainViewModel by viewModel(named<MainViewModel>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecyclerView.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        observeDataHolder(cabViewModel.usersLiveData,errorButtonClick = {
            askForSuccessOrFail()
        }){
            Adapter.builder(this)
                .addSource(UserListItemSource(it))
                .addPresenter(UserListItemPresenter(this))
                .into(mainRecyclerView)
        }

        askForSuccessOrFail()

    }

    private fun askForSuccessOrFail(){
        currentDialog?.dismiss()
        currentDialog=SapDialog(this).apply {
            messageText = "Get data from server"
            animResource= com.alperenbabagil.cabpresentation.R.raw.question_mark
            addPositiveButton(UserListRequestType.SUCCESS.str){
                cabViewModel.getUsers(UserListRequestType.SUCCESS)
            }
            addNegativeButton(UserListRequestType.FAIL.str){
                cabViewModel.getUsers(UserListRequestType.FAIL)
            }
        }.build().show()
    }

    class UserListItemSource(list: List<UserListItem>) : ListSource<UserListItem>(list){
        override fun getElementType(data: UserListItem) = 0
    }

    class UserListItemPresenter(context: Context) :
        DataBindingPresenter<UserListItem,UserListItemRowBinding>(context,{_,_,element ->
            ((context as MainActivity).cabNavigator as AppNavigator)
                .navigateToDetailPage(element.data?.username ?: "",context)
        }){
        override val elementTypes: Collection<Int> = listOf(0)

        override fun onCreateBinding(parent: ViewGroup, elementType: Int): UserListItemRowBinding {
            return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_list_item_row, parent, false)
        }

        override fun onBind(
            page: Page,
            holder: Holder,
            binding: UserListItemRowBinding,
            element: Element<UserListItem>
        ) {
            super.onBind(page, holder, binding, element)
            binding.model=element.data
        }

    }
}
