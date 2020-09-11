package io.keepcoding.eh_ho.topics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.*
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.login.LoginActivity
import kotlinx.android.synthetic.main.activity_topics.*

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity: AppCompatActivity(), TopicsFragment.TopicsInteractionListener,
            CreateTopicFragment.CreateTopicInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)
//        enableLoading()
        if(isFirstTimeCreated(savedInstanceState)) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, TopicsFragment())
                .commit()
        }
//        enableLoading(false)

/*
        val adapter = TopicsAdapter {
//          Log.d(TopicsActivity::class.java.canonicalName, it.title)
            goToPosts(it)
        }

        adapter.setTopics(TopicsRepo.topics)

        listTopics.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listTopics.adapter = adapter

//      val list: RecyclerView = findViewById(R.id.list_topics)
//      list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
*/
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


    override fun loadTopics(context: Context, topicsAdapter: TopicsAdapter) {
            enableLoading()
            context?.let {
                TopicsRepo
                    .getTopics(it,
                        {
                            //(listTopics.adapter as TopicsAdapter).setTopics(it)
                            topicsAdapter.setTopics(it)
                            enableLoading(false)
                        },
                        {
                            enableLoading(false)
                            // TODO: Manejo de errores
                        }
                    )
            }
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
}