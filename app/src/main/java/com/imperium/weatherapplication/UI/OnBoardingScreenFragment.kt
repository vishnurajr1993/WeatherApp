package com.imperium.weatherapplication.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.imperium.weatherapplication.MainActivity
import com.imperium.weatherapplication.Model.IntroSlide
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.UI.Adapters.IntroSliderAdapter
import com.imperium.weatherapplication.Utils.SCREEN_A_SUBTITLE
import com.imperium.weatherapplication.Utils.SCREEN_A_TITLE
import com.imperium.weatherapplication.Utils.SCREEN_B_SUBTITLE
import com.imperium.weatherapplication.Utils.SCREEN_B_TITLE
import com.imperium.weatherapplication.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentOnboardingScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OnBoardingScreenFragment : Fragment() {
    lateinit var binding: FragmentOnboardingScreenBinding
    private val vm: WeatherAppViewModel by viewModels()

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        binding = FragmentOnboardingScreenBinding.inflate(inflater,container,false)
        binding.viewPager.adapter = introSliderAdapter
//sets the viewpager2 to the indicator
        binding.indicator.setViewPager(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)


                    if (position == introSliderAdapter.itemCount - 1) {
//this animation is added to the finish button
                        val animation = AnimationUtils.loadAnimation(
                            requireActivity(),
                            R.anim.app_name_animation
                        )
                        binding.buttonNext.animation = animation
                        binding.buttonNext.text = "Finish"
                        binding.buttonNext.setOnClickListener {
                            lifecycleScope.launch {
                                saveOnBoarding()
                            }
                            Navigation.findNavController(binding.root).navigate(R.id.action_onBoardingScreenFragment_to_logInFragment);
                        }
                    } else {
                        binding.buttonNext.text = "Next"
                        binding.buttonNext.setOnClickListener {
                            binding.viewPager.currentItem.let {
                                binding.viewPager.setCurrentItem(it + 1, false)
                            }
                        }
                    }
                }
            })
        return binding.root
        /*loginButton=view.findViewById(R.id.login_btn_on_boarding)
        loginButton.setOnClickListener {

        }*/

    }
    fun saveOnBoarding() {
        vm.setIsOnBoardingCompleted()
    }


}