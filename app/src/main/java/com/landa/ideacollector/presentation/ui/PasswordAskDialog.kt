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
import com.landa.ideacollector.presentation.viewmodel.StateUserEnteredPassword
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PasswordAskDialog : DialogFragment() {

    private val settingsViewModel by activityViewModel<SettingsViewModel>()

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
        lifecycleScope.launch {
            settingsViewModel.userEnteredPasswordFlow.collect { state ->
                when (state) {
                    StateUserEnteredPassword.STARTEDSTATE -> return@collect
                    StateUserEnteredPassword.WRONGPASS -> Toast.makeText(
                        context,
                        "Password is wrong!",
                        Toast.LENGTH_SHORT
                    ).show()

                    StateUserEnteredPassword.CORRECTPASS -> dismiss()
                }
            }
        }
        cancelBtn.setOnClickListener {
            dismiss()
        }
        okBtn.setOnClickListener {
            val enteredPass = passwordEt.text.toString()
            settingsViewModel.userEnteredPassword(enteredPass)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        settingsViewModel.askPasswordDialogDismiss()
    }
}