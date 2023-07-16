package com.bahadir.wordle.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.wordle.R
import com.bahadir.wordle.databinding.ItemLastSearchedBinding

class LastSearchedAdapter(
    private val words: List<String>, private val onWordClick: (String) -> Unit,
) : RecyclerView.Adapter<LastSearchedAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLastSearchedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: String) {
            binding.word = word
        }
    }

    fun onWordClick(word: String) {
        onWordClick.invoke(word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLastSearchedBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_last_searched,
            parent,
            false
        )
        binding.searchAdapter = this
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = words.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(words[position])
    }
}