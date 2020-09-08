package io.keepcoding.eh_ho.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.RequestError
import io.keepcoding.eh_ho.data.SignInModel
import io.keepcoding.eh_ho.data.SignUpModel
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.topics.TopicsActivity
import io.keepcoding.eh_ho.isFirstTimeCreated
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),
    SignInFragment.SignInInteractionListener,
    SignUpFragment.SignUpInteractionListener{

    val signUpFragment = SignUpFragment()
    val signInFragment = SignInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        if (savedInstanceState == null) {
        if (isFirstTimeCreated(savedInstanceState)) {
            checkSession()
        }
/*
        val button: Button = findViewById(R.id.button_login)
        val inputUsername: EditText = findViewById(R.id.input_username)

        // 2. Definición de una clase anónima
        val listener: View.OnClickListener = object : View.OnClickListener {
            override  fun onClick(view: View?) {
                Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
            }
        }
*/
        //3. Funciones anónimas
//        val listenerLambda: (View) -> Unit = { view: View ->
//            Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
//        }

        //button.setOnClickListener(listenerLambda)
        //La opción más sencilla
/*      button.setOnClickListener{
            Toast.makeText(it?.context, "Welcome to Eh-Ho ${inputUsername.text}", Toast.LENGTH_SHORT).show()
        }
*/
//        button.setOnClickListener {
//            val intent: Intent = Intent(this, TopicsActivity::class.java)
//            startActivity(intent)
//        }

        // button.setOnClickListener (listener)
        // button.setOnClickListener ( Listener())

    }

    private fun checkSession() {
        if (UserRepo.isLogged(this.applicationContext)) {
            showTopics()
        } else {
            onGoToSignIn()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun printMessage() {
        this
    }

    //    fun showTopics(view: View) {
    private fun showTopics() {
        val intent: Intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onGoToSignUp() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, signUpFragment)
            .commit()
    }

    override fun onSignIn(signInModel: SignInModel) {
        // Autenticación
        //showTopics()
        // Toggle de vista de carga
        enableLoading()
 //     simulateLoading(signInModel)
        UserRepo.signIn(this.applicationContext,
            signInModel,
            { showTopics()},
            { error ->
                enableLoading(false)
                handleError(error)
            }
        )
    }

    private fun handleError(error: RequestError) {
        if (error.messageResId != null)
            Snackbar.make(container, error.messageResId, Snackbar.LENGTH_LONG).show()
        else if(error.message != null)
            Snackbar.make(container, error.message, Snackbar.LENGTH_LONG).show()
        else
            Snackbar.make(container, R.string.error_default, Snackbar.LENGTH_LONG).show()

    }

    override fun onGoToSignIn() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, signInFragment)
            .commit()
    }

    override fun onSignUp(signUpModel: SignUpModel) {
        enableLoading()
//      simulateLoading(signInModel)
        UserRepo.signUp(this.applicationContext,
            signUpModel,
            {
                enableLoading(false)
                Snackbar.make(container, R.string.message_sign_up, Snackbar.LENGTH_LONG).show()
                // Revisar respuesta del servidor
            },
            {
                enableLoading(false)
                handleError(it)
            }
        )
    }

    private fun enableLoading(enabled: Boolean = true) {
        if(enabled) {
            fragmentContainer.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentContainer.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }
/*
    private fun simulateLoading(signInModel: SignInModel) {
       val runnable = Runnable {
            Thread.sleep(5000)
            viewLoading.post {
                //showTopics()
                viewLoading.post{
                    UserRepo.signIn(this.applicationContext, signInModel.username)
                    showTopics()
                }
            }
        }

        Thread(runnable).start()

/*
        val task = object : AsyncTask<Long, Void, Boolean>() {
            override fun doInBackground(vararg time: Long?): Boolean {
                val second = time[1]
                Thread.sleep(time[0] ?: 3000)
                return true
            }

            override fun onPostExecute(result: Boolean?) {
                super.onPostExecute(result)
                showTopics()
            }
        }

        task.execute(5000)
 */
    }

 */
}

// 1. Definición de interfaz a partir de una clase
class Listener : View.OnClickListener {
    override fun onClick(view: View?) {
        Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
    }
}

