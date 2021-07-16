package com.example.roomworldsample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomworldsample.R
import com.example.roomworldsample.model.Description

class DescListAdapter() : ListAdapter<Description, DescListAdapter.DescViewHolder>(DescComparator()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescViewHolder
    {
        return DescViewHolder.create(parent)
    }
    
    override fun onBindViewHolder(holder: DescViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.definition)
    }
    
    class DescViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        
        fun bind(text: String?) {
            wordItemView.text = text
        }
        
        companion object {
            fun create(parent: ViewGroup): DescViewHolder
            {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.desc_recyclerview_item, parent, false)
                return DescViewHolder(view)
            }
        }
    }
    
    class DescComparator : DiffUtil.ItemCallback<Description>() {
        override fun areItemsTheSame(oldItem: Description, newItem: Description): Boolean {
            return oldItem === newItem
        }
        
        override fun areContentsTheSame(oldItem: Description, newItem: Description): Boolean {
            return oldItem.definition == newItem.definition
        }
    }
}