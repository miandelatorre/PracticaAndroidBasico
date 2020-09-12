package io.keepcoding.eh_ho.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.isFirstTimeCreated
import io.keepcoding.eh_ho.login.LoginActivity
import io.keepcoding.eh_ho.topics.TopicsAdapter
import io.keepcoding.eh_ho.topics.TopicsFragment
import kotlinx.android.synthetic.main.activity_posts.*

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity(), PostsFragment.PostsInteractionListener {

    lateinit var topicId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

            topicId = intent.getStringExtra(EXTRA_TOPIC_ID) ?:""
            val topic: Topic? = TopicsRepo.getTopic(topicId)

            if(isFirstTimeCreated(savedInstanceState)) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, PostsFragment())
                    .commit()
            }

    }

    override fun onLogout() {
        //Borrar datos
        UserRepo.logout(this.applicationContext)

        //Ir a actividad inicial
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun loadPosts(postsAdapter: PostsAdapter) {
        PostsRepo
            .getPosts(this.applicationContext,
                topicId,
                {
                        postsAdapter.setPosts(it)
                },
                {
                    // TODO: Manejo de errores
                }
            )
    }

}