package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.toExtras
import org.koin.androidx.viewmodel.ext.android.viewModel

class IdeasMenuDialog : DialogFragment() {

    private val mainViewModel: MainViewModel by viewModel()

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
        val idea = arguments?.getString("idea")
        editIdea.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (idea != null) Toast.makeText(context, "STRING", Toast.LENGTH_SHORT).show()//mainViewModel.userClickedEditIdea(idea)
            }
        }
        deleteIdea.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
//                if (idea != null) mainViewModel.userClickedDeleteIdea(idea)
            }
        }
    }
}