package com.landa.ideacollector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.landa.ideacollector.databinding.IdeasItemBinding

class IdeaAdapter : RecyclerView.Adapter<IdeaAdapter.IdeasHolder>() {
    val ideasList = ArrayList<Idea>()

    class IdeasHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = IdeasItemBinding.bind(item)

        fun bind(idea: Idea) = with(binding) {
            ibPriority.setBackgroundColor(idea.ideasPriorityColor)
            tvIdea.setText(idea.ideasText)
            tvIdeaDate.setText(idea.ideasDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeasHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ideas_item, parent, false)
        return IdeasHolder(view)
    }

    override fun onBindViewHolder(holder: IdeasHolder, position: Int) {
        holder.bind(ideasList[position])
    }

    override fun getItemCount(): Int {
        return ideasList.size
    }

    fun addIdea(idea: Idea) {
        ideasList.add(idea)
        notifyDataSetChanged()
    }


}