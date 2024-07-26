package com.landa.ideacollector.presentation.ui

import android.content.res.Resources.Theme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.NotificationCompat.getColor
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class IdeasEditMenuDialog : DialogFragment() {
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ideas_edit_menu_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextIdea = view.findViewById<EditText>(R.id.ideaEditText)
        val imageButtonPriority = view.findViewById<ImageButton>(R.id.priorityIdeaEditImageButton)
        val cancelButton = view.findViewById<Button>(R.id.editIdeaCancelButton)
        val okButton = view.findViewById<Button>(R.id.editIdeaOkButton)
        val idea = arguments?.getParcelable("idea") as Idea?
        var priorityColor = 0x00000
        if (idea != null) {
            editTextIdea.setText(idea.idea)
            priorityColor = mainViewModel.getPriorityColor(idea.priority)
            imageButtonPriority.setBackgroundColor(resources.getColor(priorityColor))
        }
        imageButtonPriority.setOnClickListener {
            val nextColor = when (resources.getColor(priorityColor)) {
                R.color.red -> R.color.yellow
                R.color.yellow -> R.color.green
                R.color.green -> R.color.red
                else -> R.color.red
            }
            priorityColor = nextColor
            imageButtonPriority.setBackgroundColor(resources.getColor(priorityColor))
        }
        okButton.setOnClickListener {
            val ideaText = editTextIdea.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                if (idea != null) mainViewModel.userClickedEditIdea(
                    idea,
                    ideaText,
                    priorityColor
                )
                else Toast.makeText(context, "Idea is not found", Toast.LENGTH_SHORT).show()
            }
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}