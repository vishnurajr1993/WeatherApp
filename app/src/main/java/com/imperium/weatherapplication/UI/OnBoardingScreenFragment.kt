package com.imperium.weatherapplication.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.imperium.weatherapplication.Model.IntroSlide
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.UI.Adapters.IntroSliderAdapter
import com.imperium.weatherapplication.Utils.SCREEN_A_SUBTITLE
import com.imperium.weatherapplication.Utils.SCREEN_A_TITLE
import com.imperium.weatherapplication.Utils.SCREEN_B_SUBTITLE
import com.imperium.weatherapplication.Utils.SCREEN_B_TITLE
import com.imperium.weatherapplication.UI.ViewModels.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentOnboardingScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OnBoardingScreenFragment : BaseFragment<FragmentOnboardingScreenBinding>() {


    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                SCREEN_A_TITLE,
                SCREEN_A_SUBTITLE,
                R.drawable.ic_baseline_cloud_24
            ),
            IntroSlide(
                SCREEN_B_TITLE,
                SCREEN_B_SUBTITLE,
                R.drawable.ic_baseline_cloud_queue_24
            ),

            )
    )

    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        setUpViewPager()
    }
    private fun setUpViewPager(){
        binding.apply {

            viewPager.adapter = introSliderAdapter
            indicator.setViewPager(binding.viewPager)
            viewPager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if (position == introSliderAdapter.itemCount - 1) {
                            val animation = AnimationUtils.loadAnimation(
                                requireActivity(),
                                R.anim.app_name_animation
                            )
                            buttonNext.animation = animation
                            buttonNext.text = "Finish"
                            buttonNext.setOnClickListener {
                                lifecycleScope.launch {
                                    saveOnBoarding()
                                }
                                Navigation.findNavController(binding.root).navigate(R.id.action_onBoardingScreenFragment_to_logInFragment);
                            }
                        } else {
                            buttonNext.text = "Next"
                            buttonNext.setOnClickListener {
                                viewPager.currentItem.let {
                                    viewPager.setCurrentItem(it + 1, false)
                                }
                            }
                        }
                    }
                })
        }
    }
    private fun saveOnBoarding() {
        vm.setIsOnBoardingCompleted()
    }

    override fun getViewBinding(): FragmentOnboardingScreenBinding =
        FragmentOnboardingScreenBinding.inflate(layoutInflater)


}