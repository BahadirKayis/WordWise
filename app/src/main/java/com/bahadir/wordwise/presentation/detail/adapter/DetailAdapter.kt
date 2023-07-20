package com.bahadir.wordwise.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.wordwise.common.extensions.visible
import com.bahadir.wordwise.databinding.ItemDetailBinding
import com.bahadir.wordwise.domain.model.DefinitionUI

class DetailAdapter(
    private var definition: List<DefinitionUI>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>(), Filterable {

    inner class ViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(definition: DefinitionUI) = with(binding) {
            " ${definition.count} - ".also { textCount.text = it }
            textDefinition.text = definition.definition
            textPartOfSpeech.text = definition.partOfSpeech
            definition.example?.let {
                textExample.text = it
                textExampleTitle.visible()
                textExample.visible()
            }
            definition.countryFlag?.let {
                imageFlag.setImageResource(it)
                imageFlag.visible()
            }
        }
    }

    fun setData(updateDef: List<DefinitionUI>) {
        definition = updateDef
        notifyItemRangeChanged(0, updateDef.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(definition[position])
    }

    override fun getItemCount(): Int = definition.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val filteredList: MutableList<DefinitionUI> = mutableListOf()

                val list = charString.toString().split(",").toList()

                for (item in list) {
                    if (item.isNotEmpty()) {
                        definition.filter {
                            it.partOfSpeech.lowercase() == item.lowercase()
                        }.forEach { filteredList.add(it) }
                    }
                }
                definition = filteredList

                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredDefinitions = results?.values as? List<*>
                filteredDefinitions?.let { def ->
                    definition = def.filterIsInstance<DefinitionUI>()
                    notifyItemRangeChanged(0, definition.size)
                }
//                results?.values?.let { def ->
//                    definition = def as List<DefinitionUI>
//                    notifyItemRangeChanged(0, definition.size)
//                }
            }
        }
    }
}