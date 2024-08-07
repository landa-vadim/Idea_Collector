package com.landa.ideacollector.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.IdeasItemBinding
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.domain.model.Priority
import com.landa.ideacollector.presentation.interfaces.RecyclerViewListener

class IdeaAdapter(private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<IdeaAdapter.IdeasHolder>() {
    private var ideasList = mutableListOf<Idea>()

    class IdeasHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = IdeasItemBinding.bind(item)

        fun bind(idea: Idea, listener: RecyclerViewListener) = with(binding) {
            val backgroundColor = when (idea.priority) {
                Priority.HIGH -> R.color.red
                Priority.MEDIUM -> R.color.yellow
                Priority.LOW -> R.color.green
            }
            priorityImageButton.setBackgroundResource(backgroundColor)
            tvIdea.text = idea.idea
            tvIdeaDate.text = idea.date
            itemView.setOnLongClickListener {
                listener.onLongClick(idea)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeasHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ideas_item, parent, false)
        return IdeasHolder(view)
    }

    override fun onBindViewHolder(holder: IdeasHolder, position: Int) {
        holder.bind(ideasList[position], listener)
    }

    override fun getItemCount(): Int {
        return ideasList.size
    }

    fun setData(newIdeasList: List<Idea>) {
        val oldList = ideasList
        val diffUtil = MyDiffUtil(oldList, newIdeasList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        ideasList.clear()
        ideasList.addAll(newIdeasList)
        diffResults.dispatchUpdatesTo(this)
    }
}