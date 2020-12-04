package com.alperenbabagil.cleanappbase.detail.presentation.happiness

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.alperenbabagil.cabpresentation.observeDataHolder
import com.alperenbabagil.cleanappbase.R
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseFragment
import com.alperenbabagil.cleanappbase.detail.presentation.DetailViewModel
import kotlinx.android.synthetic.main.happiness_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HappinessFragment() : CABDemoBaseFragment() {

    override val cabViewModel: HappinessViewModel by viewModel(named<HappinessViewModel>())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.happiness_fragment_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedViewModel : DetailViewModel = getSharedViewModel(named<DetailViewModel>())
        observeDataHolder(liveData=sharedViewModel.sharedProfileDetailLiveData){
            observeDataHolder(liveData = cabViewModel.getHappiness(it).liveData){
                gaugeView.moveToValue(it.percentage.toFloat())
            }
        }
    }
}