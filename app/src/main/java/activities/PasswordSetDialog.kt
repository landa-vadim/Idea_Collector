package activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.landa.ideacollector.R

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
        val confirmdEt = view.findViewById<EditText>(R.id.confirmEditText)
        val cancelBtn = view.findViewById<Button>(R.id.cancelButton)
        val okBtn = view.findViewById<Button>(R.id.okButton)

        cancelBtn.setOnClickListener {
            Log.e("click", "button work")

        }

    }
}


