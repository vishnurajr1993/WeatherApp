package com.imperium.weatherapplication.UI

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.imperium.weatherapplication.UI.ViewModels.WeatherAppViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {


    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

    protected val vm: WeatherAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setUpAppbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
    }

    open fun setUpViews() {}
    open fun setUpAppbar() {}
    open fun observeView() {}

    open fun observeData() {}

    private fun init() {
        binding = getViewBinding()
    }



    override fun onDestroyView() {

        super.onDestroyView()
    }
}