package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IdeasEditMenuDialog : DialogFragment() {
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ideas_edit_menu_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextIdea = view.findViewById<EditText>(R.id.ideaEditText)
        val cancelButton = view.findViewById<Button>(R.id.editIdeaCancelButton)
        val okButton = view.findViewById<Button>(R.id.editIdeaOkButton)
        val idea = arguments?.getParcelable("idea") as Idea?
        if (idea != null) {
            editTextIdea.setText(idea.idea)
        }
        okButton.setOnClickListener {
            val ideaText = editTextIdea.toString()

        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}