package com.landa.ideacollector

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.landa.ideacollector.databinding.IdeasItemBinding

class IdeaAdapter : RecyclerView.Adapter<IdeaAdapter.IdeasHolder>() {
    private val ideasList = ArrayList<Idea>()

    class IdeasHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = IdeasItemBinding.bind(item)

        fun bind(idea: Idea) = with(binding) {
            val backgroundColor = when(idea.ideasPriority) {
                Priority.HIGH -> R.color.red
                Priority.MEDIUM -> R.color.yellow
                Priority.LOW -> R.color.green
            }

            Log.d("IdeaAdapter", "priority=${idea.ideasPriority}")
            ibPriority.setBackgroundResource(backgroundColor)
            tvIdea.text = idea.ideasText
            tvIdeaDate.text = idea.ideasDate
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
        notifyItemInserted(ideasList.lastIndex)
    }
}