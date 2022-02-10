package com.imperium.weatherapplication.UI.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imperium.weatherapplication.Model.IntroSlide
import com.imperium.weatherapplication.databinding.SlideItemLayoutBinding

class IntroSliderAdapter(private val introSlides: List<IntroSlide>)
    : RecyclerView.Adapter<IntroSliderAdapter.IntroSlideViewHolder>(){

    var onTextPassed: ((textView: TextView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            SlideItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    inner class IntroSlideViewHolder(private val binding: SlideItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(introSlide: IntroSlide) {
            binding.textTitle.text = introSlide.title
            binding.textDescription.text = introSlide.description
            binding.imageSlideIcon.setImageResource(introSlide.image)
            onTextPassed?.invoke(binding.textTitle)
        }
    }
}