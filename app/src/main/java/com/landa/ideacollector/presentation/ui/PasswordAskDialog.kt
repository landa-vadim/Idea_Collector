package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.landa.ideacollector.R
import com.landa.ideacollector.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordAskDialog : DialogFragment() {

    val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.password_ask_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordEt = view.findViewById<EditText>(R.id.passwordAskEditText)
        val cancelBtn = view.findViewById<Button>(R.id.cancelAskButton)
        val okBtn = view.findViewById<Button>(R.id.okAskButton)

        cancelBtn.setOnClickListener {
            dismiss()
        }
        okBtn.setOnClickListener {
            val enteredPass = passwordEt.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                if (settingsViewModel.userEnteredPassword(enteredPass)) dismiss()
                else Toast.makeText(context, "Password is wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}