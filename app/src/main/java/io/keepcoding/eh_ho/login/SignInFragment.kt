package io.keepcoding.eh_ho.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.SignInModel
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_create_topic.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import java.lang.IllegalArgumentException

class SignInFragment: Fragment() {

    var signInInteractionListener: SignInInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //loginActivity = context as LoginActivity
        //loginActivity.printMessage()
        if (context is SignInInteractionListener)
            signInInteractionListener = context //as SignInInteractionListener
        else
            throw IllegalArgumentException("Context doesn't implement ${SignInInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_sign_in)
        //return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labelCreateAccount.setOnClickListener {
            signInInteractionListener?.onGoToSignUp()
        }
        buttonLogin.setOnClickListener {
            if(isFormValid()) {

                val signInModel = SignInModel(
                    inputUsername.text.toString(),
                    inputPassword.text.toString()
                )

                //loginActivity?.showTopics()
                signInInteractionListener?.onSignIn(signInModel)
            } else {
                showErrors()
            }
        }

    }

    private fun showErrors() {
        if(inputUsername.text.isEmpty())
            inputUsername.error = getString(R.string.error_empty)
        if(inputPassword.text.isEmpty())
            inputPassword.error = getString(R.string.error_empty)

    }

    private fun isFormValid() = inputUsername.text.isNotEmpty() && inputPassword.text.isNotEmpty()

    override fun onDetach() {
        super.onDetach()
        signInInteractionListener = null
    }


    interface SignInInteractionListener {
        fun onGoToSignUp()
        fun onSignIn(signInModel: SignInModel)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}