package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class IdeaMenuDialog : DialogFragment() {

    private val mainViewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ideas_menu_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editIdea = view.findViewById<TextView>(R.id.menuEditIdea)
        val deleteIdea = view.findViewById<TextView>(R.id.menuDeleteIdea)
        val idea = arguments?.getParcelable("idea") as Idea?
        editIdea.setOnClickListener {
            if (idea != null) {
                val args = Bundle()
                args.putParcelable("idea", idea)
                val ideaEditMenuDialog = IdeaEditMenuDialog()
                ideaEditMenuDialog.arguments = args
                ideaEditMenuDialog.show(parentFragmentManager, "ideas_edit_menu_dialog")
                dismiss()
            } else Toast.makeText(context, "idea is not found", Toast.LENGTH_SHORT).show()
        }
        deleteIdea.setOnClickListener {
            if (idea != null) {
                mainViewModel.userClickedDeleteIdea(idea)
                dismiss()
            } else Toast.makeText(context, "idea is not found", Toast.LENGTH_SHORT).show()
        }
    }
}