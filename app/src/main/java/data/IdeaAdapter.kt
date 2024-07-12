package data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import domain.dataClasses.Idea
import domain.dataClasses.Priority
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.IdeasItemBinding
import domain.utilityClasses.MyDiffUtil

class IdeaAdapter : RecyclerView.Adapter<IdeaAdapter.IdeasHolder>() {
    private var ideasList = mutableListOf<Idea>()

    class IdeasHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = IdeasItemBinding.bind(item)

        fun bind(idea: Idea) = with(binding) {
            val backgroundColor = when (idea.priority) {
                Priority.HIGH -> R.color.red
                Priority.MEDIUM -> R.color.yellow
                Priority.LOW -> R.color.green
            }
            priorityImageButton.setBackgroundResource(backgroundColor)
            tvIdea.text = idea.idea
            tvIdeaDate.text = idea.date
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

    fun setData(newIdeasList: List<Idea>) {
        lateinit var sortedIdeasList: List<Idea>
        val oldIdeasList = ideasList.sortedBy { it.id }
        val diffUtil = MyDiffUtil(oldIdeasList, newIdeasList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        sortedIdeasList = newIdeasList.sortedBy { it.date }
        ideasList = sortedIdeasList.toMutableList()
        diffResults.dispatchUpdatesTo(this)
    }
}