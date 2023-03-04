package com.bahadir.wordle.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.wordle.databinding.ItemFilterBinding

class FilterAdapter(
    private var meaningUI: List<String>,
    private val onClick: (String, Int) -> Unit
) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(string: String) {
            string.also { binding.chFilterNoun.text = it }
            binding.chFilterNoun.setOnClickListener {
                if (binding.chFilterNoun.isSelected) {
                    onClick.invoke(string, -1)
                    binding.chFilterNoun.isSelected = false
                } else {
                    onClick.invoke(string, 1)
                    binding.chFilterNoun.isSelected = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = meaningUI.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(meaningUI[position])
    }
}
