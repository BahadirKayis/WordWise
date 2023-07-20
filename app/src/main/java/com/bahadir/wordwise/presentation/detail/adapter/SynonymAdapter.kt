package com.bahadir.wordwise.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.databinding.ItemDetailSynonymBinding

class SynonymAdapter(private val synonyms: List<SynonymsItem>) :
    RecyclerView.Adapter<SynonymAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemDetailSynonymBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(string: SynonymsItem) {
            string.also { binding.chFilterNoun.text = it.word }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDetailSynonymBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = synonyms.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(synonyms[position])
    }
}