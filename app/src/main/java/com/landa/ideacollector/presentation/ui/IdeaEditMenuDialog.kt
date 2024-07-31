package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class IdeaEditMenuDialog : DialogFragment() {
    private val mainViewModel by activityViewModel<MainViewModel>()

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
        lifecycleScope.launch {
            mainViewModel.currentPriorityEditIdea.collect { state ->
                imageButtonPriority.setBackgroundColor(getColor(resources, state, null))
            }
        }
        if (idea != null) {
            editTextIdea.setText(idea.idea)
            mainViewModel.getPriorityColor(idea.priority)
        }
        imageButtonPriority.setOnClickListener {
            mainViewModel.userClickedPriorityEditIdeaButton()
        }
        okButton.setOnClickListener {
            val ideaText = editTextIdea.text.toString()
            mainViewModel.viewModelScope.launch {
                if (idea != null) {
                    mainViewModel.userClickedEditIdea(
                        idea,
                        ideaText
                    )
                    dismiss()
                } else Toast.makeText(context, "Idea is not found", Toast.LENGTH_SHORT).show()
            }
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}