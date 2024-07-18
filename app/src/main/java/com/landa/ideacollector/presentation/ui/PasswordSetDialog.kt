package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.model.Password

class PasswordSetDialog : DialogFragment() {

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

        cancelBtn.setOnClickListener {
            dismiss()
        }
        okBtn.setOnClickListener {
            val enteredPass = passwordEt.text.toString()
            val enteredConfirm = confirmEt.text.toString()
            if (enteredPass.isNotEmpty() && enteredPass == enteredConfirm) {
                val pass = Password(enteredPass)
                dismiss()
            }
            if (enteredPass.isEmpty() || enteredConfirm.isEmpty()) {
                Toast.makeText(context, "One of lines is empty", Toast.LENGTH_LONG).show()
            }
            if (enteredPass != enteredConfirm) {
                Toast.makeText(context, "Passwords not the same", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}