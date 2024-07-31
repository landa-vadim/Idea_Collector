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
import com.landa.ideacollector.presentation.viewmodel.StateUserSetPassword
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PasswordSetDialog : DialogFragment() {

    private val settingsViewModel by activityViewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.password_set_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordEt = view.findViewById<EditText>(R.id.passwordEditText)
        val confirmEt = view.findViewById<EditText>(R.id.confirmEditText)
        val cancelBtn = view.findViewById<Button>(R.id.cancelButton)
        val okBtn = view.findViewById<Button>(R.id.okButton)
        lifecycleScope.launch {
            settingsViewModel.userSetPasswordFlow.collect { state ->
                when (state) {
                    StateUserSetPassword.STARTEDSTATE -> return@collect
                    StateUserSetPassword.PASSWORDWASACCEPTED -> dismiss()
                    StateUserSetPassword.ONEFIELDISEMPTY -> Toast.makeText(
                        context,
                        "One of lines is empty",
                        Toast.LENGTH_SHORT
                    ).show()

                    StateUserSetPassword.FIELDSNOTTHESAME -> Toast.makeText(
                        context,
                        "Passwords not the same",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        cancelBtn.setOnClickListener {
            dismiss()
        }
        okBtn.setOnClickListener {
            val enteredPass = passwordEt.text.toString()
            val enteredConfirm = confirmEt.text.toString()
            settingsViewModel.userSetPassword(enteredPass, enteredConfirm)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        settingsViewModel.setPasswordDialogDismiss()
    }
}