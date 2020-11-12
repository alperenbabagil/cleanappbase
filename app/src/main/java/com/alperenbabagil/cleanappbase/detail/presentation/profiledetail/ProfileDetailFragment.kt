package com.alperenbabagil.cleanappbase.detail.presentation.profiledetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperenbabagil.cabpresentation.observeDataHolder
import com.alperenbabagil.cleanappbase.R
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseFragment
import com.alperenbabagil.cleanappbase.core.presentation.getArgument
import com.alperenbabagil.cleanappbase.core.presentation.toPrettyString
import com.alperenbabagil.cleanappbase.databinding.ProfileDetailFragmentBinding
import com.alperenbabagil.cleanappbase.databinding.UserDetailItemRowBinding
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.simpleanimationpopuplibrary.showErrorDialog
import com.otaliastudios.elements.Adapter
import com.otaliastudios.elements.Element
import com.otaliastudios.elements.Page
import com.otaliastudios.elements.extensions.DataBindingPresenter
import com.otaliastudios.elements.extensions.ListSource
import kotlinx.android.synthetic.main.profile_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.*
import kotlin.reflect.full.memberProperties

class ProfileDetailFragment : CABDemoBaseFragment() {

    override val cabViewModel: ProfileDetailViewModel by viewModel(named<ProfileDetailViewModel>())

    lateinit var userName: String

    private var selectedDelay=1

    private lateinit var  binding : ProfileDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =DataBindingUtil.inflate(
            inflater, R.layout.profile_detail_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailRecyclerView.apply {
            layoutManager= LinearLayoutManager(requireActivity())
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        delaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedDelay=progress
                delayText.text="Delay($progress seconds)"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        getSuccess.setOnClickListener {
            cabViewModel.getProfileDetail(userName, RequestResultType.SUCCESS,selectedDelay * 1000L)
        }

        getFail.setOnClickListener {
            cabViewModel.getProfileDetail(userName, RequestResultType.FAIL,selectedDelay * 1000L)
        }

        observeDataHolder(liveData = cabViewModel.profileDetailLiveData){
            fillDetail(it)
        }

        getArgument<String>(getString(R.string.userNameKey),onFound = {
            userName=it
            cabViewModel.getProfileDetail(it, RequestResultType.SUCCESS,selectedDelay * 1000L)
        }){
            showErrorDialog(errorRes = R.string.user_name_error)
        }
    }

    private fun fillDetail(profileDetail: ProfileDetail){
        binding.ppSrc=profileDetail.ppUrl
        val properties= mutableListOf<Pair<String,String>>()
        ProfileDetail::class.memberProperties.forEach {
            println("${it.name} -> ${it.get(profileDetail)}")
            val valueStr=when(val vl=it.get(profileDetail)){
                is Date -> vl.toPrettyString()
                else -> vl.toString()
            }
            properties.add(Pair("${it.name.capitalize()}:",valueStr.capitalize()))
        }
        Adapter.builder(this)
            .addSource(UserDetailItemSource(properties.toList().filter { it.first != "PpUrl:" }))
            .addPresenter(UserDetailItemPresenter(requireActivity()))
            .into(userDetailRecyclerView)
    }

    class UserDetailItemSource(list: List<Pair<String,String>>) : ListSource<Pair<String,String>>(list){
        override fun getElementType(data: Pair<String,String>) = 0
    }

    class UserDetailItemPresenter(context: Context) :
        DataBindingPresenter<Pair<String,String>, UserDetailItemRowBinding>(context){
        override val elementTypes: Collection<Int> = listOf(0)

        override fun onCreateBinding(parent: ViewGroup, elementType: Int): UserDetailItemRowBinding {
            return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_detail_item_row, parent, false)
        }

        override fun onBind(
            page: Page,
            holder: Holder,
            binding: UserDetailItemRowBinding,
            element: Element<Pair<String,String>>
        ) {
            super.onBind(page, holder, binding, element)
            binding.model=element.data
        }
    }
}