package com.alperenbabagil.cleanappbase.detail.presentation.changepp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.alperenbabagil.cabpresentation.observeDataHolder
import com.alperenbabagil.cleanappbase.R
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseFragment
import com.alperenbabagil.cleanappbase.core.presentation.getArgument
import com.alperenbabagil.simpleanimationpopuplibrary.showErrorDialog
import kotlinx.android.synthetic.main.profile_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import py.alperenbabagil.imageeditfragmentlib.fragment.fragment.DrawOnFragmentStatus
import py.alperenbabagil.imageeditfragmentlib.fragment.fragment.ImageEditFragment

class ChangePPFragment : CABDemoBaseFragment(),DrawOnFragmentStatus {
    override val cabViewModel: ChangePPViewModel by viewModel(named<ChangePPViewModel>())

    private lateinit var userName: String

    private var selectedDelay=4

    private var imageEditFragment : ImageEditFragment?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.edit_pp_fragment,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDataHolder(liveData = cabViewModel.profileDetailLiveData){
            putImageEditFragment(it.ppUrl ?: "")
        }

        getArgument<String>(getString(R.string.userNameKey),onFound = {
            userName=it
            cabViewModel.getProfileDetail(it, RequestResultType.SUCCESS,selectedDelay * 1000L)
        }){
            showErrorDialog(errorRes = R.string.user_name_error)
        }

        delaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedDelay=progress
                delayText?.text="Delay($progress seconds)"
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
    }

    private fun putImageEditFragment(ppUrl : String){
        imageEditFragment=ImageEditFragment().apply {
            arguments=Bundle().apply {
                putSerializable(ImageEditFragment.SOURCE_TYPE_KEY,
                    ImageEditFragment.SourceType.URL)
                putString(ImageEditFragment.SOURCE_DATA_KEY,ppUrl)
                putBoolean(ImageEditFragment.HIDE_SAVE_BTN_KEY,true)
            }
        }
        childFragmentManager.beginTransaction().replace(R.id.changePPFragmentContainer,
            imageEditFragment!!
            ).commit()
    }

    override fun drawingCancelled(path: String?) {
        imageEditFragment?.let {
            childFragmentManager.beginTransaction().remove(it).commit()
        }
    }

    override fun drawingCompleted(success: Boolean, path: String?) {
    }
}