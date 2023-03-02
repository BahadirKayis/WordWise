package com.bahadir.wordle.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.wordle.databinding.ItemLastSearchedBinding

class LastSearchedAdapter(
    private val words: List<String>, private val onWordClick: (String) -> Unit
) :
    RecyclerView.Adapter<LastSearchedAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemLastSearchedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: String) {
            with(binding) {
                textWord.text = word
                cons.setOnClickListener {
                    onWordClick.invoke(word)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLastSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = words.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(words[position])
    }
}