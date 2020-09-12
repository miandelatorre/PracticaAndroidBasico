package io.keepcoding.eh_ho.topics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho.*
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.login.LoginActivity
import io.keepcoding.eh_ho.posts.EXTRA_TOPIC_ID
import io.keepcoding.eh_ho.posts.PostsActivity
import kotlinx.android.synthetic.main.activity_topics.*

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity: AppCompatActivity(), TopicsFragment.TopicsInteractionListener,
            CreateTopicFragment.CreateTopicInteractionListener{

    lateinit var topicsFragment: TopicsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        if(isFirstTimeCreated(savedInstanceState)) {
            topicsFragment = TopicsFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, topicsFragment)
                .commit()
        }

        buttonRetry.setOnClickListener(){
            this.loadTopics(topicsFragment.topicsAdapter)
        }
    }


    private fun goToPosts(topic: Topic) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, topic.id)
        startActivity(intent)
    }

    override fun onCreateTopic() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CreateTopicFragment())
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }

    override fun onShowPosts(topic: Topic) {
        goToPosts(topic)
    }

    override fun onTopicCreated() {
        supportFragmentManager.popBackStack()
    }

    override fun onLogout() {
        //Borrar datos
        UserRepo.logout(this.applicationContext)

        //Ir a actividad inicial
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun loadTopics(topicsAdapter: TopicsAdapter) {
            enableLoading()
            showError(false)
                TopicsRepo
                    .getTopics(this.applicationContext/*it*/,
                        {
                            //(listTopics.adapter as TopicsAdapter).setTopics(it)
                            topicsAdapter.setTopics(it)
                            enableLoading(false)
                            showError(false)
                        },
                        {
                            enableLoading(false)
                            showError()
                            // TODO: Manejo de errores
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

    private fun showError(enabled: Boolean = true) {
        if(enabled) {
            errorLoading.visibility = View.VISIBLE
        } else {
            errorLoading.visibility = View.INVISIBLE
        }
    }
}