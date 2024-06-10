package adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import data.Idea
import data.Priority
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.IdeasItemBinding
import utils.MyDiffUtil

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
        val diffUtil = MyDiffUtil(ideasList, newIdeasList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        val newIdeasMutableList = newIdeasList.toMutableList()
        ideasList = newIdeasMutableList
        diffResults.dispatchUpdatesTo(this)

    }
}