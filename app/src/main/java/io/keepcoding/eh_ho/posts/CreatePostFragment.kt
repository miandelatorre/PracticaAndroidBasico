package io.keepcoding.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.LoadingDialogFragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.*
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.topics.CreateTopicFragment
import kotlinx.android.synthetic.main.fragment_create_post.*
import java.lang.IllegalArgumentException

const val TAG_LOADING_DIALOG = "loading_dialog"

class CreatePostFragment(topicId: String, topicTitle: String) : Fragment() {

    var topicId = topicId
    var topicTitle = topicTitle

    var interactionListener: CreatePostFragment.CreatePostInteractionListener? = null
    val loadingDialogFragment : LoadingDialogFragment by lazy {
        val message = getString(R.string.label_creating_post)
        LoadingDialogFragment.newInstance(message)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is CreatePostInteractionListener)
            this.interactionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${CreatePostInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_create_post)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topicTitle?.let {
            fragmentTopicId.text = topicTitle
        }

        if (fragmentTopicId.text.equals("")) {
            fragmentTopicId.text = topicId
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId ) {
            R.id.action_send_post -> createPost()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createPost() {

        if(isFormValid()) {

            postPost()

        } else {
            showErrors()
        }
    }


    private fun postPost() {

        enableLoadingDialog()

        val model = CreatePostModel(
            Integer(topicId.toInt()),
            inputPost.text.toString()
        )

        context?.let {
            PostsRepo.addPost(
                it.applicationContext,
                model,
                {
                    //loadingDialogFragment.dismiss()
                    enableLoadingDialog(false)
                    interactionListener?.onPostCreated()
                },
                {
                    //loadingDialogFragment.dismiss()
                    enableLoadingDialog(false)
                    handleError(it)
                }
            )
        }
    }

    private fun enableLoadingDialog(enabled: Boolean = true) {
        if (enabled)
            loadingDialogFragment.show(childFragmentManager,
                io.keepcoding.eh_ho.topics.TAG_LOADING_DIALOG
            )
        else
            loadingDialogFragment.dismiss()
    }

    private fun handleError(error: RequestError) {
        val message =
            if (error.messageResId != null)
                getString(error.messageResId)
            else error.message ?: getString(R.string.error_default)

        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
    }


    private fun showErrors() {
/*        if (inputTopic.text.isEmpty())
            inputTopic.error = getString(R.string.error_empty)*/
        if (inputPost.text.isEmpty())
            inputPost.error = getString(R.string.error_empty)

    }


    private fun isFormValid() = /*inputTopic.text.isNotEmpty() && */inputPost.text.isNotEmpty()

    interface CreatePostInteractionListener {
        fun onPostCreated()
    }

}