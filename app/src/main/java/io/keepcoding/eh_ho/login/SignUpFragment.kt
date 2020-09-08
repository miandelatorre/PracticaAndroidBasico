package io.keepcoding.eh_ho.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.SignUpModel
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.inputPassword
import kotlinx.android.synthetic.main.fragment_sign_up.inputUsername
import kotlinx.android.synthetic.main.fragment_sign_up.labelCreateAccount
import java.lang.IllegalArgumentException

class SignUpFragment : Fragment() {

    var signUpInteractionListener: SignUpInteractionListener?
            = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SignUpInteractionListener)
            signUpInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${SignUpInteractionListener::class.java.canonicalName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_sign_up)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignUp.setOnClickListener {
            if(isFormValid()) {
                val model = SignUpModel(
                    inputUsername.text.toString(),
                    inputEmail.text.toString(),
                    inputPassword.text.toString()
                )
                signUpInteractionListener?.onSignUp(model)
            } else {
                showErrors()
            }
        }

        labelCreateAccount.setOnClickListener {
            signUpInteractionListener?.onGoToSignIn()
        }
    }

    private fun showErrors() {
        if(inputEmail.text.isEmpty())
            inputEmail.error = getString(R.string.error_empty)
        if(inputUsername.text.isEmpty())
            inputUsername.error = getString(R.string.error_empty)
        if(inputPassword.text.isEmpty())
            inputPassword.error = getString(R.string.error_empty)
        if(inputConfirmPassword.text.isEmpty())
            inputConfirmPassword.error = getString(R.string.error_empty)
    }

    private fun isFormValid() = inputEmail.text.isNotEmpty() && inputUsername.text.isNotEmpty() && inputPassword.text.isNotEmpty() && inputConfirmPassword.text.isNotEmpty()

    override fun onDetach() {
        super.onDetach()
        this.signUpInteractionListener = null
    }

    interface SignUpInteractionListener {
        fun onGoToSignIn()
        fun onSignUp(signUpModel: SignUpModel)
    }
}