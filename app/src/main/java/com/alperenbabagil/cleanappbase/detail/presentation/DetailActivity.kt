package com.alperenbabagil.cleanappbase.detail.presentation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.alperenbabagil.cabpresentation.CABSAPFragment
import com.alperenbabagil.cleanappbase.R
import com.alperenbabagil.cleanappbase.core.presentation.AppNavigator
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseActivity
import com.alperenbabagil.cleanappbase.core.presentation.getExtra
import com.alperenbabagil.cleanappbase.detail.presentation.changepp.ChangePPFragment
import com.alperenbabagil.cleanappbase.detail.presentation.happiness.HappinessFragment
import com.alperenbabagil.cleanappbase.detail.presentation.profiledetail.ProfileDetailFragment
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.simpleanimationpopuplibrary.showErrorDialog
import com.ogaclejapan.smarttablayout.utils.v4.Bundler
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.detail_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import timber.log.Timber


class DetailActivity() : CABDemoBaseActivity(), ChangePPFragment.DrawHostFragmentHost {

    override val cabViewModel: DetailViewModel by viewModel(named<DetailViewModel>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        getExtra<String>(AppNavigator.USER_NAME_KEY,onFound = {
            setUI(it)
        }){
            showErrorDialog(errorRes = R.string.user_name_error
                ,positiveButtonStrRes = R.string.ok,
                positiveButtonClick = { finish() }
            )
        }
    }

    private fun setUI(userName: String){
        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add(R.string.profile_detail,
                    ProfileDetailFragment::class.java,Bundler()
                        .putString(getString(R.string.userNameKey),userName).get())
                .add(R.string.edit_pp,
                    ChangePPFragment::class.java,Bundler()
                        .putString(getString(R.string.userNameKey),userName).get())
                .add(R.string.happiness,
                    HappinessFragment::class.java,Bundler().get())
                .create()
        )



        viewPager.adapter = adapter


        viewPagerTab.setViewPager(viewPager.apply {
            offscreenPageLimit=3
        })

//        viewPagerTab.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                viewPagerTab.clearFocus()
//            }
//
//            override fun onPageSelected(position: Int) {
////                adapter.getPage(position)?.let {
////                    (it as? CABSAPFragment)?.onBackPressedCallback=
////                }
//                viewPagerTab.clearFocus()
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                viewPagerTab.clearFocus()
//            }
//
//        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun focusOnDrawing() {
        viewPager.currentItem=1
    }

}